package home.nkt.resourceservice.client;

import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto;
import home.nkt.generated.protobuf.ResourceIdDtoProto.ResourceIdDto;
import java.util.List;

public interface SongClient {

    void uploadMetaData(MetaDataDto metaDataDto);

    void deleteByResourceId(List<Long> resourceIds);

    MetaDataDto downloadMetaDataByResourceId(ResourceIdDto resourceIdDto);
}
