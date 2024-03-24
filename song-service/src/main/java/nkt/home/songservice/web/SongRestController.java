package nkt.home.songservice.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nkt.home.songservice.service.MetaDataService;
import nkt.home.songservice.service.dto.MetaDataDto;
import nkt.home.songservice.service.dto.ResourceIdDto;
import nkt.home.songservice.service.dto.ResourceIdsDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/songs")
public class SongRestController {

    static final String BASE_URL = "/v1/songs";

    private final MetaDataService metaDataService;

    @PostMapping
    public ResourceIdDto saveMetaData(@RequestBody MetaDataDto metaDataDto) {
        return metaDataService.uploadMetaData(metaDataDto);
    }

    @GetMapping("/{id}")
    public MetaDataDto getMetaData(@PathVariable("id") Long resourceId) {
        return metaDataService.downloadMetaData(resourceId);
    }

    @DeleteMapping
    public ResourceIdsDto deleteBatchMetaData(@RequestParam("id") @Valid @Size(max = 200, message = "Too many ids") List<Long> ids) {
        return metaDataService.deleteMetaDataByIds(ids);
    }
}
