package home.nkt.songservice.exception;

import org.springframework.validation.Errors;

public class ValidationException extends SongServiceAppException {

    private final Errors errors;

    public ValidationException(Errors errors) {
        this.errors = errors;
    }
}
