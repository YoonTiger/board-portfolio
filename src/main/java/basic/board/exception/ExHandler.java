package basic.board.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ExHandler {

    @ExceptionHandler(DuplicateMemberException.class)
    public ModelAndView duplicateMemberEx (DuplicateMemberException e){
        return new ModelAndView("error/duplicateMember");
    }

    @ExceptionHandler(NoSuchMemberException.class)
    public ModelAndView noSuchMemberEx (NoSuchMemberException e){
        return new ModelAndView("error/noSuchMember");
    }

    @ExceptionHandler(NoSuchBoardException.class)
    public ModelAndView noSuchBoardEx (NoSuchBoardException e){
        return new ModelAndView("error/noSuchBoard");
    }


}
