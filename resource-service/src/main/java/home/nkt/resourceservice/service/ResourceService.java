package home.nkt.resourceservice.service;

import home.nkt.resourceservice.service.dto.ResourceIdDto;
import home.nkt.resourceservice.service.dto.ResourceIdsDto;
import home.nkt.resourceservice.service.validator.Mp3File;
import jakarta.validation.Valid;
import java.util.List;

public interface ResourceService {

    ResourceIdDto upload(@Valid @Mp3File byte[] bytes);

    byte[] download(long id);

    ResourceIdsDto delete(List<Long> ids);
}
