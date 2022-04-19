package banks.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "verificationMapping",
        classes = @ConstructorResult(
                targetClass = Bank  .class,
                columns = {
                        @ColumnResult(name = "id", type=Long.class),
                        @ColumnResult(name = "bankId", type = Long.class),
                        @ColumnResult(name = "limit", type = Double.class)

                }
        )
)

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "verification")
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @ApiModelProperty("Verification id")
    public Long id;

    @Column(name = "bankId")
    @ApiModelProperty("Verification bankId")
    private Long bankId;

    @Column(name = "limited")
    @ApiModelProperty("Verification limit")
    private Double limit;
}
