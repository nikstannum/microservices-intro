package home.nkt.resourceservice.service.impl;

import com.google.protobuf.Int64Value;
import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto;
import home.nkt.generated.protobuf.ResourceIdDtoProto.ResourceIdDto;
import home.nkt.generated.protobuf.ResourceIdsDtoProto.ResourceIdsDto;
import home.nkt.resourceservice.client.SongClient;
import home.nkt.resourceservice.data.Mp3File;
import home.nkt.resourceservice.data.repository.ResourceRepository;
import home.nkt.resourceservice.exception.NotFoundException;
import home.nkt.resourceservice.exception.dto.ErrorDto;
import home.nkt.resourceservice.exception.dto.ErrorType;
import home.nkt.resourceservice.service.MetaDataService;
import home.nkt.resourceservice.service.ResourceService;
import home.nkt.resourceservice.service.mapper.ResourceIdMapper;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository repository;

    private final MetaDataService metaDataService;

    private final SongClient songClient;

    private final ResourceIdMapper resourceIdMapper;

    @Override
    public ResourceIdDto upload(byte[] bytes) {
        Blob blob;
        try {
            blob = new SerialBlob(bytes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Mp3File mp3File = new Mp3File();
        mp3File.setFile(blob);
        Long resourceId = repository.save(mp3File).getResourceId();
        saveMetaData(bytes, resourceId);
        return resourceIdMapper.convert(resourceId);
    }

    private void saveMetaData(byte[] bytes, Long resourceID) {
        MetaDataDto metaDataDto = metaDataService.getMetaData(bytes);
        metaDataDto = metaDataDto.toBuilder().setResourceId(resourceID).build();
        songClient.uploadMetaData(metaDataDto);
    }

    @Override
    public byte[] download(long id) {
        Mp3File mp3File = repository.findById(id).orElseThrow(() -> new NotFoundException(ErrorDto.builder()
                .withErrorType(ErrorType.CLIENT_ERROR)
                .withErrorMessage("Not found song by id = " + id)
                .build()));
        try (InputStream is = mp3File.getFile().getBinaryStream()) {
            return is.readAllBytes();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResourceIdsDto delete(List<Long> ids) {
        repository.deleteAllById(ids);
        songClient.deleteByResourceId(ids);
        return resourceIdMapper.convert(ids);
    }
}
