package home.nkt.resourceservice.web;

import home.nkt.resourceservice.exception.NotFoundException;
import home.nkt.resourceservice.exception.ValidationException;
import home.nkt.resourceservice.exception.dto.ErrorDto;
import home.nkt.resourceservice.exception.dto.ErrorType;
import home.nkt.resourceservice.exception.dto.ValidationResultDto;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Slf4j
@RestControllerAdvice
public class ResourceServiceExceptionTranslator {

    private static final String MSG_UNKNOWN_ERROR = "Unknown error";

    private static final String MSG_INVALID_URL = "Invalid url";

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFound(NotFoundException e) {
        log.error(String.valueOf(e));
        return e.getErrorDto();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto notValid(MethodArgumentTypeMismatchException e) {
        log.error(String.valueOf(e));
        return ErrorDto.builder()
                .withErrorType(ErrorType.CLIENT_ERROR)
                .withErrorMessage(MSG_INVALID_URL)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto notValid(HandlerMethodValidationException e) {
        log.error(String.valueOf(e));
        Map<String, List<String>> messages = e.getBeanResults().stream()
                .flatMap(p -> p.getFieldErrors().stream())
                .collect(groupingBy(FieldError::getField, mapping(FieldError::getDefaultMessage, toList())));
        return new ValidationResultDto(messages);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto notValid(ValidationException e) {
        log.error(String.valueOf(e));
        Map<String, List<String>> messages = mapErrors(e.getErrors());
        return new ValidationResultDto(messages);
    }

    private Map<String, List<String>> mapErrors(Errors rawErrors) {
        return rawErrors.getFieldErrors().stream()
                .collect(groupingBy(FieldError::getField, mapping(FieldError::getDefaultMessage, toList())));
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto serverError(Exception e) {
        log.error(String.valueOf(e));
        return ErrorDto.builder()
                .withErrorType(ErrorType.SERVER_ERROR)
                .withErrorMessage(MSG_UNKNOWN_ERROR)
                .build();
    }
}
