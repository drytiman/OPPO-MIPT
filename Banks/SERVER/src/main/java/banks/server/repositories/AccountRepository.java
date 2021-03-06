package banks.server.repositories;

import banks.server.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT con FROM Account con  WHERE con.clientId=(:id)")
    List<Account> findByClientId(@Param("id") Long id);
}
