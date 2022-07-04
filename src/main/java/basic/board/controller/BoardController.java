package basic.board.controller;

import basic.board.SessionConst;
import basic.board.controller.form.BoardForm;
import basic.board.entity.Board;
import basic.board.entity.Member;
import basic.board.paging.PageDTO;
import basic.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /** 게시글 작성 */
    @GetMapping("/write")
    public String boardCreateForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, Model model) {

        BoardForm boardForm = new BoardForm();
        boardForm.setName(member.getName());
        model.addAttribute("boardForm", boardForm);
        return "boardhtml/boardWrite";
    }

    @PostMapping("/write")
    public String boardCreate(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                              @Validated @ModelAttribute BoardForm boardForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "boardhtml/boardWrite";
        }

        Board board = Board.createBoard(member, boardForm);
        boardService.boardWrite(board);
        return "redirect:/board/list";
    }

    /** 게시글 리스트 */
    @GetMapping("/list")
    public String boardList(@ModelAttribute("pageDTO") PageDTO pageDTO, Model model) {
        List<Board> list = boardService.boardList(pageDTO);
        model.addAttribute("list", list);
        return "boardhtml/boardList";
    }


    /** 게시글 상세페이지 */
    @GetMapping("/info/{id}")
    public String boardInfo(@ModelAttribute("pageDTO") PageDTO pageDTO,
                            @PathVariable Long id, Model model) {

        Board board = boardService.boardInfo(id);
        BoardForm boardForm = BoardForm.createBoardForm(board);
        model.addAttribute("board", boardForm);
        return "boardhtml/boardInfo";
    }

    /** 게시글 수정 */
    @GetMapping("/edit/{id}")
    public String boardEditForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                @ModelAttribute("pageDTO") PageDTO pageDTO,
                                @PathVariable Long id, Model model) {

        Board board = boardService.boardInfo(id);
        String query = pageDTO.makeQueryString(pageDTO.getCurrentPageNo());

        if (!member.getName().equals(board.getName())) {
            model.addAttribute("message", "작성자만 수정가능합니다");
            model.addAttribute("redirectUri", "/board/list" + query);
            return "messagehtml/message";
        }

        BoardForm boardForm = BoardForm.createBoardForm(board);
        model.addAttribute("board", boardForm);
        return "boardhtml/boardEdit";
    }

    @PostMapping("/edit/{id}")
    public String boardUpdate(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                              @ModelAttribute("pageDTO") PageDTO pageDTO,
                              @Validated @ModelAttribute("board")BoardForm boardForm,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            log.info("error={}",boardForm);
            return "boardhtml/boardEdit";
        }

        Board board = Board.createBoard(member, boardForm);
        boardService.boardUpdate(board);

        String query = pageDTO.makeQueryString(pageDTO.getCurrentPageNo());
        model.addAttribute("message", "수정이 완료되었습니다");
        model.addAttribute("redirectUri", "/board/list"+query);
        return "messagehtml/message";
    }

    /** 게시글 삭제 */
    @GetMapping("/delete/{id}")
    public String boardDelete(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                              @ModelAttribute("pageDTO") PageDTO pageDTO,
                              @PathVariable Long id, Model model) {

        Board board = boardService.boardInfo(id);
        String query = pageDTO.makeQueryString(pageDTO.getCurrentPageNo());

        if (!member.getName().equals(board.getName())) {
            model.addAttribute("message", "작성자만 삭제가능합니다");
            model.addAttribute("redirectUri", "/board/list" + query);
            return "messagehtml/message";
        }

        boardService.boardDelete(id);

        model.addAttribute("message", "삭제가 완료되었습니다");
        model.addAttribute("redirectUri", "/board/list"+query);
        return "messagehtml/message";
    }

}
