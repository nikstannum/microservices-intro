package home.nkt.songservice.service.mapper;

import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto;
import home.nkt.songservice.data.MetaData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper
public interface MetaDataMapper {

    @Mappings({
            @Mapping(source = "year", target = "year", qualifiedByName = "mapIntegerToInt32Value"),
    })
    MetaDataDto convert(MetaData metaData);

    @Mappings({
            @Mapping(source = "year", target = "year", qualifiedByName = "toInteger"),
    })
    MetaData convert(MetaDataDto metaDataDto);

    @Named("toInteger")
    default Integer toInteger(Int32Value protoInt) {
        return protoInt == null ? null : protoInt.getValue();
    }

    @Named("mapIntegerToInt32Value")
    default Int32Value mapIntegerToInt32Value(Integer value) {
        return value != null ? Int32Value.newBuilder().setValue(value).build() : null;
    }
}
