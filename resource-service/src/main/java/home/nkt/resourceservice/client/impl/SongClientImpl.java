package home.nkt.resourceservice.client.impl;

import home.nkt.resourceservice.client.SongClient;
import home.nkt.resourceservice.service.dto.MetaDataDto;
import home.nkt.resourceservice.service.dto.ResourceIdDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class SongClientImpl implements SongClient {

    private final static String BASE_URI = "/v1/songs";
    private final WebClient webClient;
    @Value("${SONG_SERVICE_URL:http://localhost:7000}")
    private String songServiceUrl;

    @Override
    public void uploadMetaData(MetaDataDto metaDataDto) {
        String response = webClient.post()
                .uri(songServiceUrl + BASE_URI)
                .bodyValue(metaDataDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("Song client create response: " + response);
    }

    @Override
    public MetaDataDto downloadMetaDataByResourceId(ResourceIdDto resourceIdDto) {
        MetaDataDto response = webClient.get()
                .uri(songServiceUrl + BASE_URI + "/" + resourceIdDto.getId())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(MetaDataDto.class)
                .block();
        log.info("Song client get response: " + response);
        return response;
    }

    @Override
    public void deleteByResourceId(List<Long> resourceIds) {
        String response = webClient.delete()
                .uri(songServiceUrl + BASE_URI + "?id=" + resourceIds.stream().map(String::valueOf).collect(Collectors.joining(",")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("Song client delete response: " + response);
    }
}
