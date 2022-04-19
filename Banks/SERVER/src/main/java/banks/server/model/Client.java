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
        name = "clientMapping",
        classes = @ConstructorResult(
                targetClass = BankDto.class,
                columns = {
                        @ColumnResult(name = "id", type=Long.class),
                        @ColumnResult(name = "name", type=String.class),
                        @ColumnResult(name ="surname", type =String.class),
                        @ColumnResult(name ="login", type =String.class),
                        @ColumnResult(name ="password", type =String.class),
                        @ColumnResult(name ="status", type =Boolean.class)
                }
        )
)

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @ApiModelProperty("user id")
    public Long id;

    @NotNull
    @Column(name = "name", length = 30)
    @ApiModelProperty("student name")
    public String name;

    @NotNull
    @Column(name = "surname", length = 30)
    @ApiModelProperty("student surname")
    public String surname;

    @NotNull
    @Column(name = "login", length = 30)
    @ApiModelProperty("student login")
    public String login;


    @NotNull
    @Column(name = "password", length = 300)
    @ApiModelProperty("hash of password")
    @JsonIgnore
    public String password;

    @NotNull
    @Column(name = "type", length = 30)
    @ApiModelProperty("type")
    @JsonIgnore
    public String type;

    @Column(name = "passport", length = 30)
    @ApiModelProperty("passport")
    private String passport;

    @Column(name = "phone_number", length = 30)
    @ApiModelProperty("phoneNumber")
    private Integer phoneNumber;

    public boolean getStatus() {
        return status;
    }

    @Column(name = "status", length = 30)
    @ApiModelProperty("status")
    public boolean status;

}