package banks.server.repositories;

import banks.server.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BankRepository extends JpaRepository<Bank, Long> {

    @Query("SELECT con FROM Bank con  WHERE con.login=(:login)")
    Bank findBylogin(@Param("login") String login);

}