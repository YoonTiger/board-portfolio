package basic.board.entity;

import basic.board.controller.form.MemberEditForm;
import basic.board.controller.form.MemberSaveForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private Long id;

    private String name;
    private String password;
    private String city;
    private String street;
    private Integer zipcode;

    private List<Board> myBoardList;


    public Member(MemberSaveForm memberForm) {
        this.name = memberForm.getName();
        this.password = memberForm.getPassword();
        this.city = memberForm.getCity();
        this.street = memberForm.getStreet();
        this.zipcode = memberForm.getZipcode();
    }
    public Member(MemberEditForm memberForm) {
        this.id = memberForm.getId();
        this.name = memberForm.getName();
        this.password = memberForm.getPassword();
        this.city = memberForm.getCity();
        this.street = memberForm.getStreet();
        this.zipcode = memberForm.getZipcode();
    }

    public static Member createMember(MemberSaveForm memberForm){
        return new Member(memberForm);
    }

    public static Member updateMember(MemberEditForm memberForm) {
        return new Member(memberForm);
    }

}
