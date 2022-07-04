package basic.board.controller.form;


import basic.board.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class MemberSaveForm {

    @NotBlank
    @Length(min = 4, max = 10)
    private String name;

    @NotBlank
    @Length(min = 1, max = 10)
    private String password;

    @NotEmpty
    private String city;

    @NotEmpty
    private String street;

    @NotNull
    private Integer zipcode;

    public MemberSaveForm(String name, String password, String city, String street, Integer zipcode) {
        this.name = name;
        this.password = password;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}