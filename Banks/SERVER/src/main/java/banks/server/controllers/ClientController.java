package banks.server.controllers;

import banks.server.model.*;
import banks.server.repositories.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Controller
public class ClientController {


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private VerificationRepository verificationRepository;
    @Autowired
    private DeletedRepository deletedRepository;

    Authentication auth;
    Long id;

    @GetMapping("/open")
    public String account(){ return "openAcc";}


    @GetMapping("/open_deposit")
    public String accountDep(){ return "open_deposit";}

    @PostMapping("/open_deposit")
    public String openAccountDep(@RequestParam Long bank_id){

        auth = SecurityContextHolder.getContext().getAuthentication();
        id = clientRepository.findBylogin(auth.getName()).getId();

        Account account = new Account();
        account.setBankId(bank_id);
        account.setSnapDate(new Date());
        account.setClientId(id);
        account.setAccType(AccType.Deposit);
        account.setBalance(0D);
        account.setAccPercent(0D);

        ResponseEntity.ok(accountRepository.save(account));

        return "lk_client";
    }

    @GetMapping("/open_credit")
    public String accountCr(){ return "open_credit";}

    @PostMapping("/open_credit")
    public String openAccountCr(@RequestParam Long bank_id){

        auth = SecurityContextHolder.getContext().getAuthentication();
        id = clientRepository.findBylogin(auth.getName()).getId();

        Account account = new Account();
        account.setBankId(bank_id);
        account.setSnapDate(new Date());
        account.setClientId(id);
        account.setAccType(AccType.Credit);
        account.setBalance(0D);
        account.setAccPercent(0D);


        ResponseEntity.ok(accountRepository.save(account));

        return "redirect:/lk_client";
    }

    @GetMapping("/open_debit")
    public String accountDeb(){ return "open_debit";}

    @PostMapping("/open_debit")
    public String openAccountDeb(@RequestParam Long bank_id){

        auth = SecurityContextHolder.getContext().getAuthentication();
        id = clientRepository.findBylogin(auth.getName()).getId();

        Account account = new Account();
        account.setBankId(bank_id);
        account.setSnapDate(new Date());
        account.setClientId(id);
        account.setAccType(AccType.Debit);
        account.setBalance(0D);
        account.setAccPercent(0D);

        ResponseEntity.ok(accountRepository.save(account));

        return "redirect:/lk_client";
    }

    @GetMapping("/add_info")
    public String addInfo(){ return "add_info";}

    @PostMapping("/add_info")
    public String addInfoPost(@RequestParam Integer phone_number,
                              @RequestParam String passport){

        auth = SecurityContextHolder.getContext().getAuthentication();
        id = clientRepository.findBylogin(auth.getName()).getId();

        Client client = clientRepository.findById(id).get();
        client.setPhoneNumber(phone_number);
        client.setPassport(passport);
        client.setStatus(true);

        ResponseEntity.ok(clientRepository.save(client));

        return "redirect:/lk_client";
    }

    @GetMapping("/accounts")
    @ApiOperation("get all clients")
    public ResponseEntity<List<Account>> getAccounts() {
        auth = SecurityContextHolder.getContext().getAuthentication();
        id = clientRepository.findBylogin(auth.getName()).getId();
        return ResponseEntity.ok(accountRepository.findByClientId(id));
    }

    @GetMapping("/operations")
    public String operation(){ return "operation";}

    @GetMapping("/top_up")
    public String topUp(){ return "top_up";}

    @PostMapping("/top_up")
    public String topUpPost(@RequestParam Long id1,
                            @RequestParam Double sum){
        Account account = accountRepository.findById(id1).get();
        auth = SecurityContextHolder.getContext().getAuthentication();
        id = clientRepository.findBylogin(auth.getName()).getId();
        Double newBalance;
        newBalance = account.getBalance();
        newBalance += sum;
        account.setBalance(newBalance);

        Operation operation = new Operation();
        operation.setSnapDate(new Date());
        operation.setAcc(id1);
        operation.setSum(sum);
        operation.setIdClient(id);
        operation.setOperType(OperType.Пополнение);

        ResponseEntity.ok(operationRepository.save(operation));

        return "lk_client";
    }


    @GetMapping("/withdraw")
    public String withdraw(){ return "withdraw";}

    @PostMapping("/withdraw")
    public String withdrawPost(@RequestParam Long id1,
                            @RequestParam Double sum){
        Account account = accountRepository.findById(id1).get();
        AccType accType = account.getAccType();
        Long id_bank = account.getBankId();
        auth = SecurityContextHolder.getContext().getAuthentication();
        id = clientRepository.findBylogin(auth.getName()).getId();
        Client client = clientRepository.findById(id).get();
        Verification verification = verificationRepository.findByBankId(id_bank);
        Double limit = verification.getLimit();

        boolean status = client.getStatus();
        Double newBalance;
        newBalance = account.getBalance();

        if (accType.equals(AccType.Credit)) {
            if(status){
                newBalance -= sum;
                account.setBalance(newBalance);

                Operation operation = new Operation();
                operation.setSnapDate(new Date());
                operation.setAcc(id1);
                operation.setSum(-sum);
                operation.setIdClient(id);
                operation.setOperType(OperType.Снятие);

                ResponseEntity.ok(operationRepository.save(operation));
            }
            else if(!status & sum > limit){
                return "not_verification";
            }
            else {
                newBalance -= sum;
                account.setBalance(newBalance);

                Operation operation = new Operation();
                operation.setSnapDate(new Date());
                operation.setAcc(id1);
                operation.setSum(-sum);
                operation.setIdClient(id);
                operation.setOperType(OperType.Снятие);

                ResponseEntity.ok(operationRepository.save(operation));
            }
        }
        else {
            if(newBalance < sum){
                return "not_balance";
            }
            else if(status){
                newBalance -= sum;
                account.setBalance(newBalance);

                Operation operation = new Operation();
                operation.setSnapDate(new Date());
                operation.setAcc(id1);
                operation.setSum(-sum);
                operation.setIdClient(id);
                operation.setOperType(OperType.Снятие);

                ResponseEntity.ok(operationRepository.save(operation));
            }
            else if(!status & sum > limit){
                return "not_verification";
            }
            else {
                newBalance -= sum;
                account.setBalance(newBalance);

                Operation operation = new Operation();
                operation.setSnapDate(new Date());
                operation.setAcc(id1);
                operation.setSum(-sum);
                operation.setIdClient(id);
                operation.setOperType(OperType.Снятие);

                ResponseEntity.ok(operationRepository.save(operation));
            }
        }
        return "lk_client";
    }

    @GetMapping("/transfer")
    public String transfer(){ return "transfer";}

    @PostMapping("/transfer")
    public String transferPost(@RequestParam Long id1,
                               @RequestParam Long id2,
                               @RequestParam Double sum){
        Account account1 = accountRepository.findById(id1).get();
        Account account2 = accountRepository.findById(id2).get();
        Long id_bank = account1.getBankId();
        auth = SecurityContextHolder.getContext().getAuthentication();
        id = clientRepository.findBylogin(auth.getName()).getId();
        Long id3 = account2.getClientId();
        Client client = clientRepository.findById(id).get();
        Verification verification = verificationRepository.findByBankId(id_bank);
        Double limit = verification.getLimit();
        AccType accType = account1.getAccType();

        boolean status = client.getStatus();
        Double newBalance1;
        Double newBalance2;
        newBalance1 = account1.getBalance();
        newBalance2 = account2.getBalance();

        if(accType.equals(AccType.Credit)){
            if(status){
                newBalance1 -= sum;
                account1.setBalance(newBalance1);
                newBalance2 += sum;
                account2.setBalance(newBalance2);

                Operation operation1 = new Operation();
                Operation operation2 = new Operation();
                operation1.setSnapDate(new Date());
                operation1.setAcc(id1);
                operation1.setSum(-sum);
                operation1.setIdClient(id);
                operation1.setOperType(OperType.Перевод);

                operation2.setSnapDate(new Date());
                operation2.setAcc(id2);
                operation2.setSum(sum);
                operation2.setIdClient(id3);
                operation2.setOperType(OperType.Перевод);

                ResponseEntity.ok(operationRepository.save(operation1));
                ResponseEntity.ok(operationRepository.save(operation2));
            }
            else if(!status & sum > limit){
                return "not_verification";
            }
            else {
                newBalance1 -= sum;
                account1.setBalance(newBalance1);
                newBalance2 += sum;
                account2.setBalance(newBalance2);

                Operation operation1 = new Operation();
                Operation operation2 = new Operation();
                operation1.setSnapDate(new Date());
                operation1.setAcc(id1);
                operation1.setSum(-sum);
                operation1.setOperType(OperType.Перевод);

                operation2.setSnapDate(new Date());
                operation2.setAcc(id2);
                operation2.setSum(sum);
                operation2.setOperType(OperType.Перевод);

                ResponseEntity.ok(operationRepository.save(operation1));
                ResponseEntity.ok(operationRepository.save(operation2));
            }
        }

        else{
            if(newBalance1 < sum){
                return "not_balance";
            }
            else if(status){
                newBalance1 -= sum;
                account1.setBalance(newBalance1);
                newBalance2 += sum;
                account2.setBalance(newBalance2);

                Operation operation1 = new Operation();
                Operation operation2 = new Operation();
                operation1.setSnapDate(new Date());
                operation1.setAcc(id1);
                operation1.setSum(-sum);
                operation1.setIdClient(id);
                operation1.setOperType(OperType.Перевод);

                operation2.setSnapDate(new Date());
                operation2.setAcc(id2);
                operation2.setSum(sum);
                operation2.setIdClient(id3);
                operation2.setOperType(OperType.Перевод);

                ResponseEntity.ok(operationRepository.save(operation1));
                ResponseEntity.ok(operationRepository.save(operation2));
            }
            else if(!status & sum > limit){
                return "not_verification";
            }
            else {
                newBalance1 -= sum;
                account1.setBalance(newBalance1);
                newBalance2 += sum;
                account2.setBalance(newBalance2);

                Operation operation1 = new Operation();
                Operation operation2 = new Operation();
                operation1.setSnapDate(new Date());
                operation1.setAcc(id1);
                operation1.setSum(-sum);
                operation1.setOperType(OperType.Перевод);

                operation2.setSnapDate(new Date());
                operation2.setAcc(id2);
                operation2.setSum(sum);
                operation2.setOperType(OperType.Перевод);

                ResponseEntity.ok(operationRepository.save(operation1));
                ResponseEntity.ok(operationRepository.save(operation2));
            }
        }
        return "lk_client";
    }

    @GetMapping("/show_operations")
    @ApiOperation("get all clients")
    public ResponseEntity<List<Operation>> getOperations() {
        auth = SecurityContextHolder.getContext().getAuthentication();
        id = clientRepository.findBylogin(auth.getName()).getId();
        return ResponseEntity.ok(operationRepository.findByClientId(id));
    }

    @GetMapping("/canceled")
    public String canceled(){ return "canceled";}
    @PostMapping("/canceled")
    public String canceledPost(@RequestParam Long id1){
        Operation operation = operationRepository.findById(id1).get();
        Long acc_id = operation.getAcc();
        Account account = accountRepository.findById(acc_id).get();
        Long bank_id = account.getBankId();
        DeletedOperation deletedOperation = new DeletedOperation();
        deletedOperation.setOperation_id(id1);
        deletedOperation.setBank_id(bank_id);


        ResponseEntity.ok(deletedRepository.save(deletedOperation));

        return "lk_client";
    }
}
