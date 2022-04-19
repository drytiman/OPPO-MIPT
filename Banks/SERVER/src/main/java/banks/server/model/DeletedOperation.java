package banks.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import oppo.dto.BankDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;


@SqlResultSetMapping(
        name = "deletedMapping",
        classes = @ConstructorResult(
                targetClass = BankDto.class,
                columns = {
                        @ColumnResult(name = "id", type=Long.class),
                        @ColumnResult(name = "operation_id", type=Long.class),
                }
        )
)

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deleted_operations")
public class DeletedOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @ApiModelProperty("user id")
    public Long id;

    @NotNull
    @Column(name = "operation_id", length = 30)
    @ApiModelProperty("student name")
    public Long operation_id;

    @NotNull
    @Column(name = "bank_id", length = 30)
    @ApiModelProperty("student name")
    public Long bank_id;


}