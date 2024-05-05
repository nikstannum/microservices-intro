package home.nkt.songservice.service.impl;

import com.google.protobuf.Int64Value;
import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto;
import home.nkt.generated.protobuf.ResourceIdDtoProto.ResourceIdDto;
import home.nkt.generated.protobuf.ResourceIdsDtoProto.ResourceIdsDto;
import home.nkt.songservice.data.MetaData;
import home.nkt.songservice.data.repository.MetaDataRepository;
import home.nkt.songservice.service.MetaDataService;
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
        return ResourceIdDto.newBuilder()
                .setId(metaDataDto.getResourceId())
                .build();
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
        return ResourceIdsDto.newBuilder()
                .addAllIds(ids.stream()
                        .map(Int64Value::of)
                        .toList())
                .build();
    }
}
