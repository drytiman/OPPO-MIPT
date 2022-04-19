package banks.server.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @ApiModelProperty("acc id")
    private Long id;

    @Column(name = "snap_date")
    @ApiModelProperty("account snapDate")
    private Date snapDate;

    @Column(name = "client_id")
    @ApiModelProperty("account clientId")
    private Long clientId;

    @Column(name = "bank_id")
    @ApiModelProperty("account bankId")
    private Long bankId;

    @Column(name = "acc_type")
    @ApiModelProperty("account accType")
    private AccType accType;

    @Column(name = "balance")
    @ApiModelProperty("account balance")
    private Double balance;

    @Column(name = "acc_percent")
    @ApiModelProperty("account accPercent")
    private Double accPercent;

}