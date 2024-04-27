package home.nkt.resourceservice.service.mapper;

import com.google.protobuf.Int64Value;
import home.nkt.generated.protobuf.ResourceIdDtoProto.ResourceIdDto;
import home.nkt.generated.protobuf.ResourceIdsDtoProto.ResourceIdsDto;
import java.util.List;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ResourceIdMapper {

    default ResourceIdDto convert(Long id) {
        return ResourceIdDto.newBuilder()
                .setId(Int64Value.of(id))
                .build();
    }

    default Long convert(ResourceIdDto resourceIdDto) {
        return resourceIdDto.getId().getValue();
    }

    default ResourceIdsDto convert(List<Long> list) {
        List<Int64Value> int64Values = list.stream()
                .map(Int64Value::of)
                .toList();
        return ResourceIdsDto.newBuilder()
                .addAllIds(int64Values)
                .build();
    }

    default List<Long> convert(ResourceIdsDto dto) {
        return dto.getIdsList().stream()
                .map(Int64Value::getValue)
                .toList();
    }
}
