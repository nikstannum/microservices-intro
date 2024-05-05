package home.nkt.resourceservice.exception;

import home.nkt.resourceservice.exception.dto.ErrorDto;

public class NotFoundException extends ResourceServiceAppException {

    public NotFoundException(ErrorDto errorDto) {
        super(errorDto);
    }
}
