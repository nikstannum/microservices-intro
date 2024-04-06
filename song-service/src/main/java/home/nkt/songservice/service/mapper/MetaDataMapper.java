package home.nkt.songservice.service.mapper;

import home.nkt.songservice.data.MetaData;
import home.nkt.songservice.service.dto.MetaDataDto;
import org.mapstruct.Mapper;

@Mapper
public interface MetaDataMapper {

    MetaDataDto convert(MetaData metaData);

    MetaData convert(MetaDataDto metaDataDto);

}
