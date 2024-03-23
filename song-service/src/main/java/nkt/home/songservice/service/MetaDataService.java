package nkt.home.songservice.service;

import java.util.List;
import nkt.home.songservice.service.dto.MetaDataDto;
import nkt.home.songservice.service.dto.ResourceIdDto;
import nkt.home.songservice.service.dto.ResourceIdsDto;

public interface MetaDataService {

    ResourceIdDto uploadMetaData(MetaDataDto metaDataDto);

    MetaDataDto downloadMetaData(Long resourceID);

    ResourceIdsDto deleteMetaDataByIds(List<Long> ids);
}
