package banks.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import oppo.dto.ClientDto;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "roleMapping",
        classes = @ConstructorResult(
                targetClass = ClientDto.class,
                columns = {

                        @ColumnResult(name ="login", type =String.class),
                        @ColumnResult(name ="password", type =String.class),
                        @ColumnResult(name ="type", type =String.class),
                }
        )
)

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "allrole")
public class AllRole {

    @NotNull
    @Column(name = "type", length = 30)
    @ApiModelProperty("type")
    public String type;



    @NotNull
    @Column(name = "login", length = 30)
    @ApiModelProperty("login")
    public String login;


    @NotNull
    @Column(name = "password", length = 300)
    @ApiModelProperty("hash of password")
    @JsonIgnore
    public String password;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty("account id")
    private Long id;
}
