package basic.board.exception;

import java.util.NoSuchElementException;

public class NoSuchBoardException extends NoSuchElementException {

    public NoSuchBoardException() {
    }

    public NoSuchBoardException(String message) {
        super(message);
    }
}
