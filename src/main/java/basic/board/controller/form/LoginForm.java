package basic.board.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class LoginForm {

    @NotBlank
    @Length(min = 4, max = 10)
    private String name;

    @NotBlank
    @Length(min = 1, max = 10)
    private String password;

}
