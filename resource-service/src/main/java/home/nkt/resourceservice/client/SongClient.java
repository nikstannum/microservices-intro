package home.nkt.resourceservice.client;

import home.nkt.resourceservice.service.dto.MetaDataDto;
import home.nkt.resourceservice.service.dto.ResourceIdDto;
import java.util.List;

public interface SongClient {

    void uploadMetaData(MetaDataDto metaDataDto);

    void deleteByResourceId(List<Long> resourceIds);

    MetaDataDto downloadMetaDataByResourceId(ResourceIdDto resourceIdDto);
}
