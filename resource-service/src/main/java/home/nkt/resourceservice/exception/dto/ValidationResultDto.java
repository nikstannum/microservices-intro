package home.nkt.resourceservice.exception.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationResultDto extends ErrorDto {

    private static final String BASE_VALIDATION_MSG = "Sent data violates validation constraints";

    private Map<String, List<String>> messages;

    public ValidationResultDto() {
        super(ErrorType.VALIDATION_ERROR, BASE_VALIDATION_MSG);
    }

    public ValidationResultDto(Map<String, List<String>> messages) {
        this();
        this.messages = messages;
    }
}
