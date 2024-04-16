package home.nkt.songservice.service;

import home.nkt.songservice.service.dto.MetaDataDto;
import home.nkt.songservice.service.dto.ResourceIdDto;
import home.nkt.songservice.service.dto.ResourceIdsDto;
import java.util.List;

public interface MetaDataService {

    ResourceIdDto uploadMetaData(MetaDataDto metaDataDto);

    MetaDataDto downloadMetaData(Long resourceID);

    ResourceIdsDto deleteMetaDataByIds(List<Long> ids);
}
