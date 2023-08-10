package code.medconnect.domain.exception;

public class PatientNotFoundException extends RuntimeException{

    public PatientNotFoundException(final String message) {
        super(message);
    }
}
