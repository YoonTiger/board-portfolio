package basic.board.controller;

import basic.board.SessionConst;
import basic.board.controller.form.LoginForm;
import basic.board.entity.Member;
import basic.board.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;


    @GetMapping("/login")
    public String memberLoginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "memberhtml/memberLogin";
    }

    @PostMapping("/login")
    public String memberLogin(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
                              HttpServletRequest request, Model model) {

        if (bindingResult.hasErrors()) {
            return "memberhtml/memberLogin";
        }

        Member loginMember = loginService.loginCheck(form.getName(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "memberhtml/memberLogin";
        }

        String query = request.getQueryString();

        if(query == null){
            query="/";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        model.addAttribute("message", loginMember.getName() + "님 어서오세요");
        model.addAttribute("redirectUri", query);
        return "messagehtml/message";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        model.addAttribute("message", "로그아웃 되었습니다");
        model.addAttribute("redirectUri", "/");
        return "messagehtml/message";
    }
}
