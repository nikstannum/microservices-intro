package home.nkt.songservice.service.mapper;

import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto;
import home.nkt.songservice.data.MetaData;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MetaDataMapper {

    @Mappings({
            @Mapping(source = "name", target = "name", qualifiedByName = "toStringValue"),
            @Mapping(source = "artist", target = "artist", qualifiedByName = "toStringValue"),
            @Mapping(source = "album", target = "album", qualifiedByName = "toStringValue"),
            @Mapping(source = "length", target = "length", qualifiedByName = "toStringValue"),
            @Mapping(source = "resourceId", target = "resourceId", qualifiedByName = "toLongValue"),
            @Mapping(source = "year", target = "year", qualifiedByName = "mapIntegerToInt32Value"),

            @Mapping(target = "mergeFrom", ignore = true),
            @Mapping(target = "clearField", ignore = true),
            @Mapping(target = "clearOneof", ignore = true),
            @Mapping(target = "mergeName", ignore = true),
            @Mapping(target = "mergeAlbum", ignore = true),
            @Mapping(target = "mergeArtist", ignore = true),
            @Mapping(target = "mergeLength", ignore = true),
            @Mapping(target = "mergeResourceId", ignore = true),
            @Mapping(target = "mergeYear", ignore = true),
            @Mapping(target = "unknownFields", ignore = true),
            @Mapping(target = "allFields", ignore = true),
            @Mapping(target = "mergeUnknownFields", ignore = true)
    })
    MetaDataDto convert(MetaData metaData);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "name", target = "name", qualifiedByName = "toString"),
            @Mapping(source = "artist", target = "artist", qualifiedByName = "toString"),
            @Mapping(source = "album", target = "album", qualifiedByName = "toString"),
            @Mapping(source = "length", target = "length", qualifiedByName = "toString"),
            @Mapping(source = "resourceId", target = "resourceId", qualifiedByName = "toLong"),
            @Mapping(source = "year", target = "year", qualifiedByName = "toInteger")
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

    @Named("toString")
    default String toString(StringValue protoString) {
        return protoString == null ? null : protoString.getValue();
    }

    @Named("toStringValue")
    default StringValue toStringValue(String string) {
        return string != null ? StringValue.newBuilder().setValue(string).build() : null;
    }

    @Named("toLong")
    default Long toLong(Int64Value protoLong) {
        return protoLong == null ? null : protoLong.getValue();
    }

    @Named("toLongValue")
    default Int64Value toLongValue(Long value) {
        return value != null ? Int64Value.newBuilder().setValue(value).build() : null;
    }
}
