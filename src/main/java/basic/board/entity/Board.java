package basic.board.entity;

import basic.board.controller.form.BoardForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    private Long id;

    private String name;
    private String title;
    private String content;


    private Member member;

    public Board(Member member, BoardForm boardForm) {

        this.member = member;
        this.id = boardForm.getId();
        this.name = boardForm.getName();
        this.title = boardForm.getTitle();
        this.content = boardForm.getContent();
    }

    public static Board createBoard(Member member,BoardForm boardForm){
        return new Board(member, boardForm);
    }

}
