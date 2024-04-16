package home.nkt.songservice.service.impl;

import home.nkt.songservice.data.MetaData;
import home.nkt.songservice.data.repository.MetaDataRepository;
import home.nkt.songservice.service.MetaDataService;
import home.nkt.songservice.service.dto.MetaDataDto;
import home.nkt.songservice.service.dto.ResourceIdDto;
import home.nkt.songservice.service.dto.ResourceIdsDto;
import home.nkt.songservice.service.mapper.MetaDataMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MetaDataServiceImpl implements MetaDataService {

    private final MetaDataMapper metaDataMapper;

    private final MetaDataRepository metaDataRepository;

    @Override
    public ResourceIdDto uploadMetaData(MetaDataDto metaDataDto) {
        MetaData metaData = metaDataMapper.convert(metaDataDto);
        metaDataRepository.save(metaData);
        ResourceIdDto resourceIdDto = new ResourceIdDto();
        resourceIdDto.setResourceID(metaDataDto.getResourceId());
        return resourceIdDto;
    }

    @Override
    public MetaDataDto downloadMetaData(Long resourceID) {
        MetaData metaData = metaDataRepository.findByResourceId(resourceID)
                .orElseThrow(() -> new NoSuchElementException("Not found metadata by resourceId = " + resourceID));
        return metaDataMapper.convert(metaData);
    }

    @Override
    public ResourceIdsDto deleteMetaDataByIds(List<Long> ids) {
        metaDataRepository.deleteAllById(ids);
        ResourceIdsDto resourceIdsDto = new ResourceIdsDto();
        resourceIdsDto.setIds(ids);
        return resourceIdsDto;
    }
}
