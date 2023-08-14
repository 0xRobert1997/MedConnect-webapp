package code.medconnect.api.controller;

import code.medconnect.domain.exception.NotFoundException;
import code.medconnect.domain.exception.VisitInTakenTimePeriodException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

/*    private static final Map<Class<?>, HttpStatus> EXCEPTION_STATUS = Map.of(
            ConstraintViolationException.class, HttpStatus.BAD_REQUEST,
            DataIntegrityViolationException.class, HttpStatus.BAD_REQUEST,
            EntityNotFoundException.class, HttpStatus.NOT_FOUND,
            NotFoundException.class, HttpStatus.NOT_FOUND,
            VisitInTakenTimePeriodException.class, HttpStatus.BAD_REQUEST
    );
    public HttpStatus getHttpStatusFromException(final Class<?> exception) {
        return EXCEPTION_STATUS.getOrDefault(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNoResourceFound(NotFoundException ex) {
        String message = String.format("Could not find a resource: [%s]", ex.getMessage());
        log.error(message, ex);
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", message);
        return modelView;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleException(BindException ex) {
        String message = String.format("Bad request for field: [%s], wrong value: [%s]",
                Optional.ofNullable(ex.getFieldError()).map(FieldError::getField).orElse(null),
                Optional.ofNullable(ex.getFieldError()).map(FieldError::getRejectedValue).orElse(null));
        log.error(message, ex);
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", message);
        return modelView;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleAccessDeniedException(AccessDeniedException ex) {
        String message ="Access denied!";
        log.error(message, ex);
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", message);
        return modelView;
    }

/*    @ExceptionHandler(ProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(ProcessingException ex) {
        String message = String.format("Processing exception occurred: [%s]", ex.getMessage());
        log.error(message, ex);
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", message);
        return modelView;
    }*/
}
