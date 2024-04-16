package home.nkt.resourceservice.exception;

import home.nkt.resourceservice.exception.dto.ErrorDto;

public class NotFoundException extends AppException {

    public NotFoundException(ErrorDto errorDto) {
        super(errorDto);
    }
}
