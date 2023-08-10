package code.medconnect.domain.exception;

public class DoctorNotFoundException extends RuntimeException{

    public DoctorNotFoundException(final String message) {
        super(message);
    }
}
