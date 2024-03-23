package home.nkt.resourceservice.service;

import home.nkt.resourceservice.service.dto.MetaDataDto;

public interface MetaDataService {

    MetaDataDto getMetaData(byte[] data);
}
