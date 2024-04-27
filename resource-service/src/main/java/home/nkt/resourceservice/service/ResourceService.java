package home.nkt.resourceservice.service;

import home.nkt.generated.protobuf.ResourceIdDtoProto.ResourceIdDto;
import home.nkt.generated.protobuf.ResourceIdsDtoProto.ResourceIdsDto;
import java.util.List;

public interface ResourceService {

    ResourceIdDto upload(byte[] bytes);

    byte[] download(long id);

    ResourceIdsDto delete(List<Long> ids);
}
