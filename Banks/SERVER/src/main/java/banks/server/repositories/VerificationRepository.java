package banks.server.repositories;

import banks.server.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    @Query("SELECT con FROM Verification con  WHERE con.bankId=(:id)")
    Verification findByBankId(@Param("id") Long id);
}
