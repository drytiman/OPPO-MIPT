package banks.server.controllers;

import banks.server.model.Bank;
import banks.server.model.Client;
import banks.server.repositories.BankRepository;
import banks.server.repositories.ClientRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CbContoller {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BankRepository bankRepository;

    @GetMapping("/clients")
    @ApiOperation("get all clients")
    public ResponseEntity<List<Client>> getClients() {
        return ResponseEntity.ok(clientRepository.findAll());
    }


    @GetMapping("/banks")
    @ApiOperation("get all banks")
    public ResponseEntity<List<Bank>> getBanks() {
        return ResponseEntity.ok(bankRepository.findAll());
    }
}
