package banks.server.repositories;

import banks.server.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT con FROM Client con  WHERE con.login=(:login)")
    Client findBylogin(@Param("login") String login);

}
