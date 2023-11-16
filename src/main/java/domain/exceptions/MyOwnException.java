package domain.exceptions;

import java.util.Optional;

public class MyOwnException extends Exception {

    private final Exception previousException;
    private final ExceptionMessage message;


    public MyOwnException(ExceptionMessage message, Exception previousException) {
        this.previousException = previousException;
        this.message = message;
    }

    public ExceptionMessage getExceptionMessage() {
        return message;
    }

    public Optional<Exception> getInnerException() {
        return Optional.ofNullable(previousException);
    }

    @Override
    public String getMessage() {
        return this.message.getContent();
    }
}
