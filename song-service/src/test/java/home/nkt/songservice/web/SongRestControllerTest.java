package home.nkt.songservice.web;

import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;
import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto;
import home.nkt.generated.protobuf.ResourceIdDtoProto.ResourceIdDto;
import home.nkt.generated.protobuf.ResourceIdsDtoProto.ResourceIdsDto;
import home.nkt.songservice.service.MetaDataService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_PROTOBUF;
import static org.springframework.http.MediaType.APPLICATION_PROTOBUF_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class SongRestControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @MockBean
    private MetaDataService service;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void checkSaveMetaDataShouldReturnEquals() throws Exception {
        // given
        ResourceIdDto resourceIdDto = ResourceIdDto.newBuilder()
                .setId(Int64Value.of(1L))
                .build();

        when(service.uploadMetaData(any())).thenReturn(resourceIdDto);

        MetaDataDto metaDataDto = MetaDataDto.newBuilder()
                .setName("name")
                .build();

        ResourceIdDto expected = ResourceIdDto.newBuilder()
                .setId(Int64Value.of(1L))
                .build();

        // when - then
        mvc.perform(post(SongRestController.BASE_URL)
                        .contentType(APPLICATION_PROTOBUF)
                        .content(metaDataDto.toByteArray()))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_PROTOBUF))
                .andExpect(content().bytes(expected.toByteArray()));
    }

    @Test
    void checkGetMetaDataShouldReturnEquals() throws Exception {
        // given
        MetaDataDto metaDataDto = MetaDataDto.newBuilder()
                .setResourceId(1L)
                .setName("name")
                .setArtist("artist")
                .setAlbum("album")
                .setLength("03:00:00")
                .setYear(Int32Value.of(2000))
                .build();

        when(service.downloadMetaData(1L)).thenReturn(metaDataDto);

        byte[] expected = metaDataDto.toByteArray();

        // when - then
        mvc.perform(get(SongRestController.BASE_URL + "/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_PROTOBUF_VALUE))
                .andExpect(content().bytes(expected));
    }

    @Test
    void checkDeleteBatchMetaData() throws Exception {
        // given
        ResourceIdsDto expected = ResourceIdsDto.newBuilder()
                .addIds(Int64Value.of(1L))
                .build();
        when(service.deleteMetaDataByIds(Collections.singletonList(1L))).thenReturn(expected);

        // then
        mvc.perform(delete(SongRestController.BASE_URL)
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROTOBUF_VALUE))
                .andExpect(content().bytes(expected.toByteArray()));
    }

    @Test
    void checkDeleteBatchMetaDataShouldReturnBadRequest() throws Exception {
        // given
        List<String> listIds = new ArrayList<>(201);
        for (int i = 1; i <= 201; i++) {
            listIds.add(String.valueOf(i));
        }
        String ids = StringUtils.join(listIds, ",");

        // then
        mvc.perform(delete(SongRestController.BASE_URL)
                        .param("id", ids))
                .andExpect(status().isBadRequest());
    }
}
