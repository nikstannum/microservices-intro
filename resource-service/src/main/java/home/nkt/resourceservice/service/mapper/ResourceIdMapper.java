package home.nkt.resourceservice.service.mapper;

import home.nkt.resourceservice.service.dto.ResourceIdDto;
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
}
