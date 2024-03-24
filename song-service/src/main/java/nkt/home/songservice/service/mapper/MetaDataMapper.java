package nkt.home.songservice.service.mapper;

import nkt.home.songservice.data.MetaData;
import nkt.home.songservice.service.dto.MetaDataDto;
import org.mapstruct.Mapper;

@Mapper
public interface MetaDataMapper {

    MetaDataDto convert(MetaData metaData);

    MetaData convert(MetaDataDto metaDataDto);

}
