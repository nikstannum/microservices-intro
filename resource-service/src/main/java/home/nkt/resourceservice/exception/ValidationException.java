package home.nkt.resourceservice.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class ValidationException extends ResourceServiceAppException {

    private final Errors errors;

    public ValidationException(Errors errors) {
        this.errors = errors;
    }
}
