package home.nkt.resourceservice.service.impl;

import home.nkt.resourceservice.client.SongClient;
import home.nkt.resourceservice.data.Mp3File;
import home.nkt.resourceservice.data.repository.ResourceRepository;
import home.nkt.resourceservice.service.MetaDataService;
import home.nkt.resourceservice.service.ResourceService;
import home.nkt.resourceservice.service.dto.MetaDataDto;
import home.nkt.resourceservice.service.dto.ResourceIdDto;
import home.nkt.resourceservice.service.dto.ResourceIdsDto;
import home.nkt.resourceservice.service.mapper.ResourceIdMapper;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
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
        Long resourceID = repository.save(mp3File).getResourceId();
        saveMetaData(bytes, resourceID);
        return resourceIdMapper.convert(resourceID);
    }

    private void saveMetaData(byte[] bytes, Long resourceID) {
        MetaDataDto metaData = metaDataService.getMetaData(bytes);
        metaData.setResourceId(resourceID);
        songClient.uploadMetaData(metaData);
    }

    @Override
    public byte[] download(long id) {
        Mp3File mp3File = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found song by id = " + id));
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
