package banks.server.model;

import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@SqlResultSetMapping(
        name = "operationMapping",
        classes = @ConstructorResult(
                targetClass = Bank  .class,
                columns = {
                        @ColumnResult(name = "id", type=Long.class),
                        @ColumnResult(name = "snapDate", type = Date.class),
                        @ColumnResult(name = "acc", type = String.class),
                        @ColumnResult(name = "summ", type = Double.class)

                }
        )
)

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @ApiModelProperty("operation id")
    public Long id;

    @Column(name = "snapDate")
    @ApiModelProperty("operation snapDate")
    private Date snapDate;

    @Column(name = "acc")
    @ApiModelProperty("operation acc")
    private Long acc;

    @Column(name = "client_id")
    @ApiModelProperty("operation acc")
    private Long idClient;

    @Column(name = "summ")
    @ApiModelProperty("operation sum")
    private Double sum;

    @Column(name = "type")
    @ApiModelProperty("operation sum")
    private OperType operType;

}