package nkt.home.songservice.service.mapper;

import nkt.home.songservice.data.MetaData;
import nkt.home.songservice.service.dto.MetaDataDto;
import org.mapstruct.Mapper;

@Mapper
public interface MetaDataMapper {

    MetaDataDto convert(MetaData metaData);

    MetaData convert(MetaDataDto metaDataDto);

//    default MetaDataDto convert(MetaData metaData) {
//        if (metaData == null) {
//            return null;
//        } else {
//            MetaDataDto metaDataDto = new MetaDataDto();
//            metaDataDto.setName(metaData.getName());
//            metaDataDto.setArtist(metaData.getArtist());
//            metaDataDto.setAlbum(metaData.getAlbum());
//            metaDataDto.setLength(metaData.getLength());
//            metaDataDto.setResourceId(metaData.getResourceId());
//            metaDataDto.setYear(metaData.getYear());
//            return metaDataDto;
//        }
//    }
//
//    default MetaData convert(MetaDataDto metaDataDto) {
//        if (metaDataDto == null) {
//            return null;
//        } else {
//            MetaData metaData = new MetaData();
//            metaData.setName(metaDataDto.getName());
//            metaData.setArtist(metaDataDto.getArtist());
//            metaData.setAlbum(metaDataDto.getAlbum());
//            metaData.setLength(metaDataDto.getLength());
//            metaData.setResourceId(metaDataDto.getResourceId());
//            metaData.setYear(metaDataDto.getYear());
//            return metaData;
//        }
//    }

}
