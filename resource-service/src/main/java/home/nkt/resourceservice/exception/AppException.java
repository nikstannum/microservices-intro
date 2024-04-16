package home.nkt.resourceservice.exception;

import home.nkt.resourceservice.exception.dto.ErrorDto;
import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private ErrorDto errorDto;

    public AppException() {
    }

    public AppException(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }
}
