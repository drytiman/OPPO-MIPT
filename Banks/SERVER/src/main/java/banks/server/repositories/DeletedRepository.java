package banks.server.repositories;

import banks.server.model.DeletedOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeletedRepository extends JpaRepository<DeletedOperation, Long> {
    @Query("SELECT con FROM DeletedOperation con  WHERE con.bank_id=(:id)")
    List<DeletedOperation> findByBankId(@Param("id") Long id);

    @Query("SELECT con FROM DeletedOperation con  WHERE con.operation_id=(:id)")
    DeletedOperation findByOperationId(@Param("id") Long id);
}