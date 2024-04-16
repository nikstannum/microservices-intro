package home.nkt.resourceservice.exception.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorType {

    SERVER_ERROR("Server error"),

    CLIENT_ERROR("Client error"),

    VALIDATION_ERROR("Validation error");

    private final String errorType;

    ErrorType(String errorType) {
        this.errorType = errorType;
    }

    @JsonCreator
    public static ErrorType fromString(String value) {
        for (ErrorType errorType : ErrorType.values()) {
            if (errorType.errorType.equalsIgnoreCase(value)) {
                return errorType;
            }
        }
        throw new IllegalArgumentException("Unknown error type: " + value);
    }

    @JsonValue
    public String getErrorType() {
        return errorType;
    }
}
