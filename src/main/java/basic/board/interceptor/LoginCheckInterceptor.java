package basic.board.interceptor;

import basic.board.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();

        log.info("로그인 체크 인터셉터 실행 {}", requestURI);
        log.info("로그인 체크 인터셉터 실행 {}", queryString);


        HttpSession session = request.getSession();

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
            log.info("미인증 사용자 요청");

            response.sendRedirect("/login?"+requestURI+"?"+queryString);
            return false;
        }
        return true;
    }
}
