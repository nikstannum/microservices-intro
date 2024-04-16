package home.nkt.resourceservice.service.mapper;

import home.nkt.resourceservice.service.dto.ResourceIdDto;
import home.nkt.resourceservice.service.dto.ResourceIdsDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper
public interface ResourceIdMapper {

    default ResourceIdDto convert(Long id) {
        ResourceIdDto resourceIdDto = new ResourceIdDto();
        resourceIdDto.setId(id);
        return resourceIdDto;
    }

    default Long convert(ResourceIdDto resourceIdDto) {
        return resourceIdDto == null ? null : resourceIdDto.getId();
    }

    default ResourceIdsDto convert(List<Long> ids) {
        ResourceIdsDto result = new ResourceIdsDto();
        result.setIds(ids);
        return result;
    }

    default List<Long> convert(ResourceIdsDto resourceIdsDto) {
        return resourceIdsDto.getIds();
    }
}
