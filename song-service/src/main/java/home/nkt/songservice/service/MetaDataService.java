package home.nkt.songservice.service;

import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto;
import home.nkt.generated.protobuf.ResourceIdDtoProto.ResourceIdDto;
import home.nkt.generated.protobuf.ResourceIdsDtoProto.ResourceIdsDto;
import java.util.List;

public interface MetaDataService {

    ResourceIdDto uploadMetaData(MetaDataDto metaDataDto);

    MetaDataDto downloadMetaData(Long resourceID);

    ResourceIdsDto deleteMetaDataByIds(List<Long> ids);
}
