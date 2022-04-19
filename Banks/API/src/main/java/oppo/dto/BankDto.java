package oppo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@SuppressWarnings("unused")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankDto {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    /**
     * Название банка
     * */
    @NotNull
    private String login;

    @NotNull
    private String password;

}
