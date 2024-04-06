package home.nkt.songservice.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import home.nkt.songservice.service.MetaDataService;
import home.nkt.songservice.service.dto.MetaDataDto;
import home.nkt.songservice.service.dto.ResourceIdDto;
import home.nkt.songservice.service.dto.ResourceIdsDto;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        ResourceIdDto resourceIdDto = new ResourceIdDto();
        resourceIdDto.setResourceID(1L);

        when(service.uploadMetaData(any())).thenReturn(resourceIdDto);

        // when - then
        mvc.perform(post(SongRestController.BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(new MetaDataDto())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.resourceID", is(1)));
    }

    @Test
    void checkGetMetaDataShouldReturnEquals() throws Exception {
        // given
        MetaDataDto metaDataDto = new MetaDataDto();
        metaDataDto.setResourceId(1L);
        metaDataDto.setName("name");
        metaDataDto.setArtist("artist");
        metaDataDto.setAlbum("album");
        metaDataDto.setLength("03:00:00");
        metaDataDto.setYear(2000);

        when(service.downloadMetaData(1L)).thenReturn(metaDataDto);

        // when - then
        mvc.perform(get(SongRestController.BASE_URL + "/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.resourceId", is(1)))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.artist", is("artist")))
                .andExpect(jsonPath("$.album", is("album")))
                .andExpect(jsonPath("$.length", is("03:00:00")))
                .andExpect(jsonPath("$.year", is(2000)));
    }

    @Test
    void checkDeleteBatchMetaData() throws Exception {
        // given
        ResourceIdsDto dto = new ResourceIdsDto();
        dto.setIds(Collections.singletonList(1L));
        when(service.deleteMetaDataByIds(Collections.singletonList(1L))).thenReturn(dto);

        // then
        mvc.perform(delete(SongRestController.BASE_URL)
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ids", is(Collections.singletonList(1))));
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
