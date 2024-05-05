package home.nkt.resourceservice.exception;

import home.nkt.resourceservice.exception.dto.ErrorDto;
import lombok.Getter;

@Getter
public class ResourceServiceAppException extends RuntimeException {

    private ErrorDto errorDto;

    public ResourceServiceAppException() {
    }

    public ResourceServiceAppException(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }
}
