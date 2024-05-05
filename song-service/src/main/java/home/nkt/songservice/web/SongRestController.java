package home.nkt.songservice.web;

import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto;
import home.nkt.generated.protobuf.ResourceIdDtoProto.ResourceIdDto;
import home.nkt.generated.protobuf.ResourceIdsDtoProto.ResourceIdsDto;
import home.nkt.songservice.exception.ValidationException;
import home.nkt.songservice.service.MetaDataService;
import home.nkt.songservice.service.validator.MetaDataDtoValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/songs")
public class SongRestController {

    static final String BASE_URL = "/v1/songs";

    private final MetaDataService metaDataService;

    private final MetaDataDtoValidator metaDataDtoValidator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceIdDto saveMetaData(@RequestBody @Valid MetaDataDto metaDataDto, Errors errors) {
        checkErrors(errors);
        return metaDataService.uploadMetaData(metaDataDto);
    }

    private void checkErrors(Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }

    @GetMapping("/{id}")
    public MetaDataDto getMetaData(@PathVariable("id") Long resourceId) {
        return metaDataService.downloadMetaData(resourceId);
    }

    @DeleteMapping
    public ResourceIdsDto deleteBatchMetaData(@RequestParam("id") @Valid @Size(max = 200, message = "Too many ids") List<Long> ids) {
        return metaDataService.deleteMetaDataByIds(ids);
    }

    @InitBinder
    private void initBind(WebDataBinder binder) {
        Object target = binder.getTarget();
        if (target != null && target.getClass().equals(MetaDataDto.class)) {
            binder.setValidator(metaDataDtoValidator);
        }
    }
}
