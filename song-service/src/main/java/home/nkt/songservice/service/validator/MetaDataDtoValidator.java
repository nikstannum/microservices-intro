package home.nkt.songservice.service.validator;

import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MetaDataDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MetaDataDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MetaDataDto metaDataDto = (MetaDataDto) target;
        if (!metaDataDto.hasResourceId()) {
            errors.rejectValue("resourceId", null, "resourceId has to be not null");
        }
    }
}
