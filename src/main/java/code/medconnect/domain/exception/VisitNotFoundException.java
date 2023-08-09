package code.medconnect.domain.exception;

public class VisitNotFoundException extends RuntimeException{

    public VisitNotFoundException(final String message) {
        super(message);
    }
}
