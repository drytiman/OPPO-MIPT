package banks.server.repositories;

import banks.server.model.CentralBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CentralBankRepository extends JpaRepository<CentralBank, Long> {
    @Query("SELECT con FROM CentralBank con  WHERE con.login=(:login)")
    CentralBank findBylogin(@Param("login") String login);
}
