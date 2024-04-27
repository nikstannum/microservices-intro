package home.nkt.songservice.service.impl;

import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;
import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto;
import home.nkt.generated.protobuf.ResourceIdDtoProto.ResourceIdDto;
import home.nkt.generated.protobuf.ResourceIdsDtoProto.ResourceIdsDto;
import home.nkt.songservice.service.MetaDataService;
import home.nkt.songservice.service.mapper.MetaDataMapper;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SpringBootTest
class MetaDataServiceImpIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MetaDataService service;
    @Autowired
    private MetaDataMapper metaDataMapper;

    @Test
    void checkUploadMetaDataShouldReturnEquals() {
        // given
        MetaDataDto dto = getNewMetaDataDto();

        ResourceIdDto expected = ResourceIdDto.newBuilder()
                .setId(Int64Value.of(3L))
                .build();

        // when
        ResourceIdDto actual = service.uploadMetaData(dto);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private MetaDataDto getNewMetaDataDto() {
        return MetaDataDto.newBuilder()
                .setName("Name")
                .setArtist("Artist")
                .setAlbum("Album")
                .setLength("03:00:00")
                .setYear(Int32Value.of(2010))
                .setResourceId(3L)
                .build();
    }

    @Test
    void checkDownloadMetaDataShouldReturnEquals() {
        // given
        MetaDataDto expected = getExistingMetaDataDto();

        // when
        MetaDataDto actual = service.downloadMetaData(2L);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private MetaDataDto getExistingMetaDataDto() {
        return MetaDataDto.newBuilder()
                .setName("Grape")
                .setArtist("Nesoloduha")
                .setAlbum("Best songs")
                .setLength("03:00:00")
                .setYear(Int32Value.of(2000))
                .setResourceId(2L)
                .build();
    }

    @Test
    void checkDownloadMetaDataShouldThrowNoSuchElmExc() {
        // when - then
        Assertions.assertThrows(NoSuchElementException.class, () -> service.downloadMetaData(100L));
    }

    @Test
    void deleteMetaDataByIdsShouldReturnEquals() {
        // given
        List<Long> ids = Arrays.asList(1L, 2L);

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

    @Test
    void deleteMetaDataByIdsWhenThereIsNoExistingIdShouldReturnEquals() {
        // given
        List<Long> ids = Arrays.asList(1L, 2L, 100L);

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
}
