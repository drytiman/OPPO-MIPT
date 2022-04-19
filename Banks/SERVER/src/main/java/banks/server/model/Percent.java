package banks.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "percentMapping",
        classes = @ConstructorResult(
                targetClass = Bank  .class,
                columns = {
                        @ColumnResult(name = "id", type=Long.class),
                        @ColumnResult(name = "bankId", type = Long.class),
                        @ColumnResult(name = "accType", type = String.class),
                        @ColumnResult(name = "sumFrom", type = Double.class),
                        @ColumnResult(name = "sumTo", type = Double.class),
                        @ColumnResult(name = "percent", type = Double.class),
                        @ColumnResult(name = "commission", type = Double.class)

                }
        )
)

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "percent")
public class Percent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @ApiModelProperty("percent id")
    public Long id;

    @Column(name = "bankId")
    @ApiModelProperty("percent bankId")
    private Long bankId;

    @Column(name = "accType")
    @ApiModelProperty("percent accType")
    private AccType accType;

    @Column(name = "sumFrom")
    @ApiModelProperty("percent sumFrom")
    private Double sumFrom;

    @Column(name = "sumTo")
    @ApiModelProperty("percent sumTo")
    private Double sumTo;

    @Column(name = "percent")
    @ApiModelProperty("percent percent")
    private Double percent;

    @Column(name = "commission")
    @ApiModelProperty("percent commission")
    private Double commission;
}
