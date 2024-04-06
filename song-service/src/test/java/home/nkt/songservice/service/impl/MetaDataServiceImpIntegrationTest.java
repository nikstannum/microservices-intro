package home.nkt.songservice.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import home.nkt.songservice.service.MetaDataService;
import home.nkt.songservice.service.dto.MetaDataDto;
import home.nkt.songservice.service.dto.ResourceIdDto;
import home.nkt.songservice.service.dto.ResourceIdsDto;
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

    @Test
    void checkUploadMetaDataShouldReturnEquals() {
        // given
        MetaDataDto dto = getNewMetaDataDto();

        ResourceIdDto expected = new ResourceIdDto();
        expected.setResourceID(3L);

        // when
        ResourceIdDto actual = service.uploadMetaData(dto);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private MetaDataDto getNewMetaDataDto() {
        MetaDataDto dto = new MetaDataDto();
        dto.setName("Name");
        dto.setArtist("Artist");
        dto.setAlbum("Album");
        dto.setLength("03:00:00");
        dto.setYear(2010);
        dto.setResourceId(3L);
        return dto;
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
        MetaDataDto metaDataDto = new MetaDataDto();
        metaDataDto.setName("Grape");
        metaDataDto.setArtist("Nesoloduha");
        metaDataDto.setAlbum("Best songs");
        metaDataDto.setLength("03:00:00");
        metaDataDto.setYear(2000);
        metaDataDto.setResourceId(2L);
        return metaDataDto;
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

        ResourceIdsDto expected = new ResourceIdsDto();
        expected.setIds(ids);

        // when
        ResourceIdsDto actual = service.deleteMetaDataByIds(ids);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteMetaDataByIdsWhenThereIsNoExistingIdShouldReturnEquals() {
        // given
        List<Long> ids = Arrays.asList(1L, 2L, 100L);

        ResourceIdsDto expected = new ResourceIdsDto();
        expected.setIds(ids);

        // when
        ResourceIdsDto actual = service.deleteMetaDataByIds(ids);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}