package banks.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import oppo.dto.ClientDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "teacherMapping",
        classes = @ConstructorResult(
                targetClass = ClientDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "login", type = String.class),
                        @ColumnResult(name = "password", type = String.class)
                }
        )
)

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @ApiModelProperty("bank id")
    public Long id;

    @NotNull
    @Column(name = "type", length = 30)
    @ApiModelProperty("bank")
    public String type;


    @NotNull
    @Column(name = "login", length = 30)
    @ApiModelProperty("bank login")
    public String login;


    @NotNull
    @Column(name = "password", length = 300)
    @ApiModelProperty("hash of password")
    @JsonIgnore
    public String password;

}