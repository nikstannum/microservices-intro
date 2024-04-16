package home.nkt.resourceservice.exception.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@Builder(setterPrefix = "with")
@JsonPropertyOrder({ErrorDto.TYPE, ErrorDto.MESSAGE})
public class ErrorDto {

    protected final static String TYPE = "type";

    protected final static String MESSAGE = "message";

    private ErrorType errorType;

    private String errorMessage;

    @JsonGetter(TYPE)
    public String getErrorTypeValue() {
        return errorType.getErrorType();
    }

    public void setErrorTypeValue(String errorType) {
        this.errorType = ErrorType.fromString(errorType);
    }

    @JsonGetter(MESSAGE)
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}


