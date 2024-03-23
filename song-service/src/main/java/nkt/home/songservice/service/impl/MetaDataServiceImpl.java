package nkt.home.songservice.service.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import nkt.home.songservice.data.MetaData;
import nkt.home.songservice.data.repository.MetaDataRepository;
import nkt.home.songservice.service.MetaDataService;
import nkt.home.songservice.service.dto.MetaDataDto;
import nkt.home.songservice.service.dto.ResourceIdDto;
import nkt.home.songservice.service.dto.ResourceIdsDto;
import nkt.home.songservice.service.mapper.MetaDataMapper;
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
        Long id = metaDataRepository.save(metaData).getId();
        ResourceIdDto resourceIdDto = new ResourceIdDto();
        resourceIdDto.setResourceID(id);
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
