package home.nkt.resourceservice.service.impl;

import com.google.protobuf.Int64Value;
import home.nkt.generated.protobuf.ResourceIdDtoProto.ResourceIdDto;
import home.nkt.generated.protobuf.ResourceIdsDtoProto.ResourceIdsDto;
import home.nkt.resourceservice.client.SongClient;
import home.nkt.resourceservice.data.repository.ResourceRepository;
import home.nkt.resourceservice.service.MetaDataService;
import home.nkt.resourceservice.service.ResourceService;
import home.nkt.resourceservice.service.mapper.ResourceIdMapper;
import jakarta.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ResourceServiceImplIntegrationTest {


    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private MetaDataService metaDataService;

    @Mock
    private SongClient songClient;

    @Autowired
    private ResourceIdMapper mapper;


    private ResourceService resourceService;

    @BeforeEach
    void setUp() {
        resourceService = new ResourceServiceImpl(
                resourceRepository,
                metaDataService,
                songClient,
                mapper);
    }

    @Test
    void checkUploadShouldReturnResourceIdNotNull() throws IOException {
        // given
        doNothing().when(songClient).uploadMetaData(any());

        // when
        ResourceIdDto uploaded = resourceService.upload(getBytes());

        // then
        Assertions.assertThat(uploaded.getId()).isNotNull();
    }

    @Test
    @Sql("/sql/data.sql")
    void checkDownloadShouldReturnEquals() throws IOException {
        // given
        byte[] expected = getBytes();

        // when
        byte[] actual = resourceService.download(1L);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DirtiesContext
    @Sql("/sql/data.sql")
    void delete() {
        // given
        doNothing().when(songClient).deleteByResourceId(any());
        ResourceIdsDto expected = ResourceIdsDto.newBuilder()
                .addAllIds(Collections.singletonList(Int64Value.of(1L)))
                .build();

        // when
        ResourceIdsDto actual = resourceService.delete(Collections.singletonList(1L));

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    private byte[] getBytes() throws IOException {
        Path path = Paths.get("src", "test", "resources", "songs", "Lesnik.mp3");
        try (InputStream is = new FileInputStream(path.toFile())) {
            return is.readAllBytes();
        }
    }
}
