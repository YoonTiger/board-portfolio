package basic.board.controller.form;


import basic.board.entity.Member;
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
public class MemberEditForm {

    @NotNull
    private Long id;

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

    public MemberEditForm(Long id, String name, String password, String city, String street, Integer zipcode) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.password = password;
        this.street = street;
        this.zipcode = zipcode;
    }

    public static MemberEditForm createMemberEditForm(Member member){
        return new MemberEditForm(member.getId(),member.getName(),member.getPassword()
                ,member.getCity(),member.getStreet(),member.getZipcode());
    }
}