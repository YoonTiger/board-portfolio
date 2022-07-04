package basic.board.exception;

import java.util.NoSuchElementException;

public class NoSuchMemberException extends NoSuchElementException {

    public NoSuchMemberException() {
    }

    public NoSuchMemberException(String Message) {
        super(Message);
    }
}
