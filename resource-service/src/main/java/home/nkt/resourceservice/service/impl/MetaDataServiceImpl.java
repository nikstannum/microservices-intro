package home.nkt.resourceservice.service.impl;

import home.nkt.resourceservice.service.MetaDataService;
import home.nkt.resourceservice.service.dto.MetaDataDto;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

@Service
public class MetaDataServiceImpl implements MetaDataService {

    private static final String DURATION_FORMAT = "%02d:%02d:%02d";

    @Override
    public MetaDataDto getMetaData(byte[] data) {
        try {
            Metadata metadata = new Metadata();

            Mp3Parser mp3Parser = new Mp3Parser();
            mp3Parser.parse(new ByteArrayInputStream(data), new BodyContentHandler(), metadata, new ParseContext());

            return buildMetaDataDto(metadata);
        } catch (TikaException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private MetaDataDto buildMetaDataDto(Metadata metadata) {
        MetaDataDto dto = new MetaDataDto();
        dto.setName(metadata.get("dc:title"));
        dto.setArtist(metadata.get("xmpDM:albumArtist"));
        dto.setAlbum(metadata.get("xmpDM:album"));
        dto.setLength(getDuration(metadata));
        dto.setYear(getYear(metadata));
        return dto;
    }

    private Integer getYear(Metadata metadata) {
        String raw = metadata.get("xmpDM:releaseDate");
        return StringUtils.isBlank(raw) ? null : Integer.valueOf(raw);
    }

    private String getDuration(Metadata metadata) {
        String raw = metadata.get("xmpDM:duration");
        if (raw == null) {
            return null;
        }
        double seconds = Double.parseDouble(raw);
        Duration duration = Duration.ofSeconds((long) seconds);
        return String.format(DURATION_FORMAT, duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
    }
}
