package basic.board.exception;



public class DuplicateMemberException extends IllegalStateException{

    public DuplicateMemberException() {
    }

    public DuplicateMemberException(String message) {
        super(message);
    }
}
