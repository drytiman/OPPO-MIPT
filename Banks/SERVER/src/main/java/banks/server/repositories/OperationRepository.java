package banks.server.repositories;

import banks.server.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("SELECT con FROM Operation con  WHERE con.idClient=(:id)")
    List<Operation> findByClientId(@Param("id") Long id);
}
