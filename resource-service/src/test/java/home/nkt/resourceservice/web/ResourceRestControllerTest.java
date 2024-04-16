package home.nkt.resourceservice.web;

import home.nkt.resourceservice.service.MetaDataService;
import home.nkt.resourceservice.service.ResourceService;
import home.nkt.resourceservice.service.dto.MetaDataDto;
import home.nkt.resourceservice.service.dto.ResourceIdDto;
import home.nkt.resourceservice.service.dto.ResourceIdsDto;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ResourceRestController.class)
class ResourceRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MetaDataService metaDataService;

    @MockBean
    private ResourceService resourceService;

    @Test
    void checkUploadShouldReturnEquals() throws Exception {
        // given
        ResourceIdDto expected = new ResourceIdDto();
        expected.setId(1L);
        when(resourceService.upload(any())).thenReturn(expected);

        MockMultipartFile multipartFile = new MockMultipartFile("multipartFile", getBytes());

        // when - then
        mvc.perform(multipart(HttpMethod.POST, ResourceRestController.BASE_URL)
                        .file(multipartFile))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    private byte[] getBytes() throws IOException {
        Path path = Paths.get("src", "test", "resources", "songs", "Король и Шут - Лесник.mp3");
        try (InputStream is = new FileInputStream(path.toFile())) {
            return is.readAllBytes();
        }
    }

    @Test
    void checkUploadShouldReturnBadRequest() throws Exception {
        // given
        ResourceIdDto expected = new ResourceIdDto();
        expected.setId(1L);
        when(resourceService.upload(any())).thenReturn(expected);

        MockMultipartFile multipartFile = new MockMultipartFile("multipartFile", new byte[1]);

        // when - then
        mvc.perform(multipart(HttpMethod.POST, ResourceRestController.BASE_URL)
                        .file(multipartFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messages.file[0]", is("Sent file is not mp3 file")));
    }

    @Test
    void checkDownloadShouldReturnEquals() throws Exception {
        // given
        byte[] bytes = new byte[1];
        when(resourceService.download(1L)).thenReturn(bytes);
        MetaDataDto metaDataDto = new MetaDataDto();
        metaDataDto.setName("song");
        when(metaDataService.getMetaData(bytes)).thenReturn(metaDataDto);

        // when - then
        mvc.perform(get(ResourceRestController.BASE_URL + "/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("audio/mpeg")))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=song.mp3"))
                .andExpect(content().bytes(bytes));
    }

    @Test
    void checkDeleteShouldReturnOk() throws Exception {
        // given
        ResourceIdsDto dto = new ResourceIdsDto();
        dto.setIds(Collections.singletonList(1L));
        when(resourceService.delete(Collections.singletonList(1L))).thenReturn(dto);

        // then
        mvc.perform(delete(ResourceRestController.BASE_URL)
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ids", is(Collections.singletonList(1))));
    }

    @Test
    void checkDeleteShouldReturnBadRequest() throws Exception {
        // given
        List<String> listIds = new ArrayList<>(201);
        for (int i = 1; i <= 201; i++) {
            listIds.add(String.valueOf(i));
        }
        String ids = StringUtils.join(listIds, ",");

        // then
        mvc.perform(delete(ResourceRestController.BASE_URL)
                        .param("id", ids))
                .andExpect(status().isBadRequest());
    }
}