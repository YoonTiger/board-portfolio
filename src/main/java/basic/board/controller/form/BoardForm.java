package basic.board.controller.form;

import basic.board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class BoardForm {

    private Long id;
    private String name;

    @NotEmpty
    @Length(min = 1, max = 20)
    private String title;

    @NotEmpty
    private String content;

    public BoardForm(Long id, String name, String title, String content) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
    }

    public static BoardForm createBoardForm(Board board){
        return new BoardForm(board.getId(), board.getName(), board.getTitle(),board.getContent());
    }
}
