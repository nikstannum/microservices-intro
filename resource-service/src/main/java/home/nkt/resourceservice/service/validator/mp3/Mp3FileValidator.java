package home.nkt.resourceservice.service.validator.mp3;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.io.IOException;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Mp3FileValidator implements ConstraintValidator<Mp3File, MultipartFile> {


    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        Tika tika = new Tika();
        try {
            String detectedType = tika.detect(multipartFile.getInputStream());
            boolean isMp3 = "audio/mpeg".equals(detectedType);
            if (!isMp3) {
                context.buildConstraintViolationWithTemplate("Sent file is not mp3 file")
                        .addPropertyNode("file")
                        .addConstraintViolation();
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
