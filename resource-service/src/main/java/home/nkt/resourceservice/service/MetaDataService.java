package home.nkt.resourceservice.service;


import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto;

public interface MetaDataService {

    MetaDataDto getMetaData(byte[] data);
}
