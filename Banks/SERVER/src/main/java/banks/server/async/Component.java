/*package english.server.async;

import english.server.model.*;
import english.server.repositories.AccountRepository;
import english.server.repositories.BankRepository;
import english.server.repositories.CpRepository;
import english.server.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;

@Service
public class Component {

    @Autowired
    public final AccountRepository accountRepository;

    @Autowired
    public final BankRepository bankRepository;

    @Autowired
    public final CpRepository cpRepository;

    @Autowired
    public final OperationRepository operationRepository;

    public Component(AccountRepository accountRepository, BankRepository bankRepository, CpRepository cpRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
        this.cpRepository = cpRepository;
        this.operationRepository = operationRepository;
    }


    @Scheduled(fixedRate = 3000)
    @Async
    public void refreshPercent(){

        //получаем список акк
        List<Account> accountList = accountRepository.findAll();

        ListIterator<Account> iterator = accountList.listIterator();
        Account account;

        while (iterator.hasNext()){

            if(iterator.next().getAccType().equals(AccType.Credit)){
                //получаем акк
                account = iterator.next();
                //получаем Процент
                Percent percent = cpRepository.findByIdAnAndAccType(account.getBankId(), AccType.Credit);

                if(account.getBalance() < 0){
                    account.setBalance(account.getBalance() - percent.getCommission());
                    iterator.set(account);
                    Operation operation = new Operation();
                    operation.setIdClient(account.getClientId());
                    operation.setSnapDate(new Date());
                    operation.setAcc(account.getId());
                    operation.setOperType(OperType.Начисление_комиссии);
                    operation.setSum(percent.getCommission());
                    ResponseEntity.ok(operationRepository.save(operation));
                    accountRepository.save(account);
                }
            }

            else if (iterator.next().getAccType().equals(AccType.Deposit)){
                account = iterator.next();
                Double balance = account.getBalance();
                List<Percent> percents = cpRepository.findAll();
                Double percentForAcc = 0D;
                for(Percent p: percents){
                    if(balance > p.getSumFrom() && balance < p.getSumTo()){
                        percentForAcc = p.getPercent();
                    }
                }
                balance += percentForAcc;
                account.setBalance(balance);
                iterator.set(account);
            }

            else {

            }
            accountRepository.saveAll(accountList);
        }
    }
}
*/