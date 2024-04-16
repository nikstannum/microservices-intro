package home.nkt.resourceservice.service;

import home.nkt.resourceservice.service.dto.ResourceIdDto;
import home.nkt.resourceservice.service.dto.ResourceIdsDto;
import java.util.List;

public interface ResourceService {

    ResourceIdDto upload(byte[] bytes);

    byte[] download(long id);

    ResourceIdsDto delete(List<Long> ids);
}
