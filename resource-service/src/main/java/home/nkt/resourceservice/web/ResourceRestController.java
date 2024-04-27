package home.nkt.resourceservice.web;

import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto;
import home.nkt.generated.protobuf.ResourceIdDtoProto.ResourceIdDto;
import home.nkt.generated.protobuf.ResourceIdsDtoProto.ResourceIdsDto;
import home.nkt.resourceservice.service.MetaDataService;
import home.nkt.resourceservice.service.ResourceService;
import home.nkt.resourceservice.service.validator.mp3.Mp3File;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("unused")
@RestController
@RequiredArgsConstructor
@RequestMapping(ResourceRestController.BASE_URL)
public class ResourceRestController {

    static final String BASE_URL = "/v1/resources";

    private final ResourceService resourceService;

    private final MetaDataService metaDataService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceIdDto upload(@RequestPart("multipartFile") @Mp3File MultipartFile multipartFile) {
        byte[] bytes;
        try {
            bytes = multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resourceService.upload(bytes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") long id) {
        byte[] data = resourceService.download(id);
        ByteArrayResource resource = new ByteArrayResource(data);
        MetaDataDto metaData = metaDataService.getMetaData(data);
        String encodedFileName = URLEncoder.encode(metaData.getName() + ".mp3", StandardCharsets.UTF_8);
        encodedFileName = encodedFileName.replaceAll("\\+", "%20");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .contentLength(data.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName)
                .body(resource);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResourceIdsDto delete(@RequestParam("id") @Valid @Size(max = 200, message = "Too many ids") List<Long> ids) {
        return resourceService.delete(ids);
    }
}
