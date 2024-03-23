package home.nkt.resourceservice.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.Tika;

public class Mp3FileValidator implements ConstraintValidator<Mp3File, InputStream> {

    @Override
    public boolean isValid(InputStream value, ConstraintValidatorContext context) {
        Tika tika = new Tika();
        try {
            String detectedType = tika.detect(value.readAllBytes());
            return "audio/mpeg".equals(detectedType);
        } catch (IOException e) {
            return false;
        }
    }
}
