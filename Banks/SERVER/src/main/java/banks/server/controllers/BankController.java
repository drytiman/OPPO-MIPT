package banks.server.controllers;

import banks.server.model.*;
import banks.server.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class BankController {


    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private CpRepository cpRepository;

    @Autowired
    private DeletedRepository deletedRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/verification")
    public String verification(){ return "verification"; }

    Authentication auth;
    Long id;

    @PostMapping("/verification")
    public String verificationClient(@RequestParam Double limited){

        auth = SecurityContextHolder.getContext().getAuthentication();
        id = bankRepository.findBylogin(auth.getName()).getId();

        Verification verification = new Verification();
        verification.setBankId(id);
        verification.setLimit(limited);

        ResponseEntity.ok(verificationRepository.save(verification));

        return "lk_bank";
    }

    @GetMapping("/commission_percent")
    public String commissionPercent(){ return "commission_percent"; }

    @GetMapping("/commission")
    public String commission(){ return "commission"; }

    @PostMapping("/commission")
    public String commissionForClient(@RequestParam Double commission){

        auth = SecurityContextHolder.getContext().getAuthentication();
        id = bankRepository.findBylogin(auth.getName()).getId();

        Percent percent = new Percent();
        percent.setBankId(id);
        percent.setAccType(AccType.Credit);
        percent.setCommission(commission);

        ResponseEntity.ok(cpRepository.save(percent));

        return "lk_bank";
    }

    @GetMapping("/percent/debit")
    public String percent(){ return "percentDebit"; }

    @PostMapping("/percent/debit")
    public String percentForDebit(@RequestParam Double percent){

        auth = SecurityContextHolder.getContext().getAuthentication();
        id = bankRepository.findBylogin(auth.getName()).getId();

        Percent newPercent = new Percent();
        newPercent.setBankId(id);
        newPercent.setAccType(AccType.Debit);
        newPercent.setPercent(percent);

        ResponseEntity.ok(cpRepository.save(newPercent));

        return "lk_bank";
    }

    @GetMapping("/percent/deposit")
    public String deposit(){ return "percentDeposit"; }

    @PostMapping("/percent/deposit")
    public String percentForDeposit(@RequestParam Double percent,
                                    @RequestParam Double sumFrom,
                                    @RequestParam Double sumTo){

        Percent newPercent = new Percent();
        newPercent.setBankId(id);
        newPercent.setAccType(AccType.Deposit);
        newPercent.setSumFrom(sumFrom);
        newPercent.setSumTo(sumTo);
        newPercent.setPercent(percent);

        ResponseEntity.ok(cpRepository.save(newPercent));

        return "lk_bank";
    }

    @GetMapping("/canceled_show")
    public ResponseEntity<List<DeletedOperation>> canceled_show(){
        auth = SecurityContextHolder.getContext().getAuthentication();
        id = bankRepository.findBylogin(auth.getName()).getId();
        return ResponseEntity.ok(deletedRepository.findByBankId(id)); }

    @GetMapping("/canceled_operation")
    public String canceled_operation(){
        return "canceled_operation";}

    @PostMapping("/canceled_operation")
    public String canceled_operationPost(@RequestParam Long id1){
        DeletedOperation deletedOperation = deletedRepository.findByOperationId(id1);
        Operation operation = operationRepository.findById(id1).get();
        Double sum = operation.getSum();
        Long acc_id = operation.getAcc();
        Account account = accountRepository.findById(acc_id).get();
        Double new_balance = account.getBalance();
        new_balance -= sum;
        account.setBalance(new_balance);
        operationRepository.delete(operation);
        deletedRepository.delete(deletedOperation);

        return "lk_bank";
    }

    @Scheduled(initialDelayString = "1000", fixedDelayString = "60000")
    @GetMapping("/calc")
    public String calc(){
        List<Account> accountList = accountRepository.findAll();
        for(Account account: accountList){
            Operation operation = new Operation();
            Double balance = account.getBalance();
            Long bank_id = account.getBankId();
            if(account.getAccType().equals(AccType.Credit)) {
                Double commission = cpRepository.findByIdAnAndAccType(bank_id, AccType.Credit).getCommission();
                operation.setSum(-commission);
                operation.setOperType(OperType.Начисление_комиссии);
                operation.setAcc(account.getId());
                operation.setSnapDate(new Date());
                operation.setIdClient(account.getClientId());
                if (balance < 0) {
                    balance -= commission;
                    account.setBalance(balance);
                    ResponseEntity.ok(accountRepository.save(account));
                    ResponseEntity.ok(operationRepository.save(operation));
                }
            }
            else if(account.getAccType().equals(AccType.Debit)){
                Double percent = cpRepository.findByIdAnAndAccType(bank_id, AccType.Debit).getPercent();
                Double add_percent = account.getAccPercent();
                System.out.println();
                add_percent += balance * (percent/36500);
                ResponseEntity.ok(accountRepository.save(account));
            }
            else {
                Double percent = cpRepository.findByIdAndTypeAndSum(bank_id, AccType.Deposit, account.getBalance()).getPercent();
                Double add_percent = account.getAccPercent();
                System.out.println();
                add_percent += balance * (percent/36500);
                account.setAccPercent(add_percent);
                ResponseEntity.ok(accountRepository.save(account));
            }
        }
        return "lk_bank";
    }

    @Scheduled(initialDelayString = "600000", fixedDelayString = "600000")
    @GetMapping("/calcAll")
    public String calcAll(){
        List<Account> accountList = accountRepository.findAll();
        for(Account account: accountList){
            Operation operation = new Operation();
            Double balance = account.getBalance();
            Double add_percent = account.getAccPercent();
            account.setBalance(balance + add_percent);
            account.setAccPercent(0D);
            ResponseEntity.ok(accountRepository.save(account));
            operation.setIdClient(account.getClientId());
            operation.setSnapDate(new Date());
            operation.setOperType(OperType.Начисление_процентов);
            operation.setSum(add_percent);
            operation.setAcc(account.getId());
            ResponseEntity.ok(operationRepository.save(operation));
        }
        return "lk_bank";
    }
}
