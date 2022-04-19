package banks.server.repositories;

import banks.server.model.AccType;
import banks.server.model.Percent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CpRepository extends JpaRepository<Percent, Long> {

    @Query("SELECT con FROM Percent con  WHERE con.bankId=(:bankId)")
    Percent findByBankId(@Param("bankId") Long bankId);

    @Query("SELECT con FROM Percent con  WHERE con.bankId=(:bankId) AND con.accType =(:accType)")
    Percent findByIdAnAndAccType(@Param("bankId") Long bankId, @Param("accType") AccType accType);
    @Query("SELECT con FROM Percent con  WHERE con.bankId=(:bankId) AND con.accType =(:accType) AND con.sumFrom <= (:sum) AND con.sumTo > (:sum)")
    Percent findByIdAndTypeAndSum(@Param("bankId") Long bankId, @Param("accType") AccType accType, @Param("sum") Double sum);
}
