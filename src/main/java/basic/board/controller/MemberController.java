package basic.board.controller;
import basic.board.SessionConst;
import basic.board.controller.form.MemberEditForm;
import basic.board.controller.form.MemberSaveForm;
import basic.board.entity.Board;
import basic.board.entity.Member;
import basic.board.paging.PageDTO;
import basic.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 가입
     */
    @GetMapping("/join")
    public String memberCreateForm(@ModelAttribute("member") MemberSaveForm memberForm) {
        return "memberhtml/memberJoin";
    }

    @PostMapping("/join")
    public String memberCreate(@Validated @ModelAttribute("member") MemberSaveForm memberForm,
                               BindingResult bindingResult, HttpServletRequest request, Model model) {

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "memberhtml/memberJoin";
        }

        Member member = Member.createMember(memberForm);
        memberService.join(member);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        model.addAttribute("message", member.getName() + "님 반갑습니다");
        model.addAttribute("redirectUri", "/");
        return "messagehtml/message";
    }

    /**
     * 회원 전체조회
     */
    @GetMapping("/list")
    public String memberList(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "memberhtml/memberList";
    }

    /**
     * 회원 상세정보
     */
    @GetMapping("/info")
    public String memberInfoForm(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                    Member member, @ModelAttribute("pageDTO") PageDTO pageDTO, Model model) {

        Member findMember = memberService.findById(member.getId());
        MemberEditForm memberForm = MemberEditForm.createMemberEditForm(findMember);
        model.addAttribute("member", memberForm);
        return "memberhtml/memberInfo";
    }

    /**
     * 회원 삭제
     */
    @GetMapping("/delete/{id}")
    public String memberDelete(@PathVariable Long id, HttpServletRequest request, Model model) {

        memberService.delete(id);

        HttpSession session = request.getSession(false);
        session.invalidate();

        model.addAttribute("message", "탈퇴가 완료되었습니다");
        model.addAttribute("redirectUri", "/");
        return "messagehtml/message";
    }

    /**
     * 회원 수정
     */
    @GetMapping("/edit/{id}")
    public String memberEditForm(@ModelAttribute("pageDTO") PageDTO pageDTO, @PathVariable Long id, Model model) {

        Member member = memberService.findById(id);
        MemberEditForm memberForm = MemberEditForm.createMemberEditForm(member);
        model.addAttribute("member", memberForm);
        return "memberhtml/memberEdit";
    }

    @PostMapping("/edit/{id}")
    public String memberUpdate(@Validated @ModelAttribute("member") MemberEditForm memberForm,
                               BindingResult bindingResult, @ModelAttribute("pageDTO") PageDTO pageDTO,
                               Model model) {

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "memberhtml/memberEdit";
        }

        Member member = Member.updateMember(memberForm);
        memberService.update(member);

        String query = pageDTO.makeQueryString(pageDTO.getCurrentPageNo());
        model.addAttribute("message", "수정이 완료되었습니다");
        model.addAttribute("redirectUri", "/board/list"+ query);
        return "messagehtml/message";
    }

    /**
     * 내가 작성한 게시글 리스트
     */
    @GetMapping("/myBoardList/{id}")
    public String myBoardList(@PathVariable Long id, Model model) {
        List<Board> boards = memberService.myBoardList(id);
        model.addAttribute("boards", boards);
        return "memberhtml/myBoardList";
    }
}
