package home.nkt.songservice.service.impl;

import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto;
import home.nkt.generated.protobuf.ResourceIdDtoProto.ResourceIdDto;
import home.nkt.generated.protobuf.ResourceIdsDtoProto.ResourceIdsDto;
import home.nkt.songservice.data.MetaData;
import home.nkt.songservice.data.repository.MetaDataRepository;
import home.nkt.songservice.service.mapper.MetaDataMapper;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetaDataServiceImplTest {

    @Mock
    private MetaDataRepository repository;

    @Mock
    private MetaDataMapper mapper;

    @InjectMocks
    private MetaDataServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new MetaDataServiceImpl(mapper, repository);
    }

    @Test
    void checkUploadMetaDataShouldReturnEquals() {
        // given
        when(mapper.convert(getMetaDataDto())).thenReturn(getMetaDataWithoutId());
        MetaData metaData = getMetaDataWithoutId();
        metaData.setId(1L);
        when(repository.save(any())).thenReturn(metaData);

        ResourceIdDto expected = ResourceIdDto.newBuilder()
                .setId(Int64Value.of(1L))
                .build();

        // when
        ResourceIdDto actual = service.uploadMetaData(getMetaDataDto());

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkDownloadMetaDataShouldReturnEquals() {
        // given
        MetaData metaData = getMetaDataWithoutId();
        when(repository.findByResourceId(1L)).thenReturn(Optional.of(metaData));

        MetaDataDto expected = getMetaDataDto();
        when(mapper.convert(metaData)).thenReturn(expected);

        // when
        MetaDataDto actual = service.downloadMetaData(1L);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkDownloadMetaDataShouldThrowNoSuchElmExc() {
        // given
        when(repository.findByResourceId(1L)).thenReturn(Optional.empty());

        // when - then
        Assertions.assertThrows(NoSuchElementException.class, () -> service.downloadMetaData(1L));
    }

    @Test
    void checkDeleteMetaDataByIdsShouldReturnEquals() {
        // given
        doNothing().when(repository).deleteAllById(anyCollection());

        List<Long> ids = Arrays.asList(1L, 2L, 3L);

        ResourceIdsDto expected = ResourceIdsDto.newBuilder()
                .addAllIds(ids.stream()
                        .map(Int64Value::of)
                        .toList())
                .build();

        // when
        ResourceIdsDto actual = service.deleteMetaDataByIds(ids);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private MetaData getMetaDataWithoutId() {
        MetaData metaData = new MetaData();
        metaData.setName("name");
        metaData.setAlbum("album");
        metaData.setArtist("artist");
        metaData.setYear(2024);
        metaData.setLength("00:03:00");
        metaData.setResourceId(1L);
        return metaData;
    }

    private MetaDataDto getMetaDataDto() {
        return MetaDataDto.newBuilder()
                .setName(StringValue.of("name"))
                .setAlbum(StringValue.of("album"))
                .setArtist(StringValue.of("artist"))
                .setYear(Int32Value.of(2024))
                .setLength(StringValue.of("00:03:00"))
                .setResourceId(Int64Value.of(1L))
                .build();
    }
}