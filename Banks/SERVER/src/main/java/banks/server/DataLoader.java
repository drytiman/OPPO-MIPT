package banks.server;


import banks.server.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private ClientRepository clientRepository;


    @Autowired
    public DataLoader(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void run(ApplicationArguments args) {

    }
}
