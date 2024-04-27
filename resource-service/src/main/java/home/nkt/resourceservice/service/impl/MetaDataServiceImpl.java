package home.nkt.resourceservice.service.impl;

import com.google.protobuf.Int32Value;
import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto;
import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto.Builder;
import home.nkt.resourceservice.service.MetaDataService;
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
        String name = metadata.get("dc:title");
        Integer year = getYear(metadata);
        String artist = metadata.get("xmpDM:albumArtist");
        String album = metadata.get("xmpDM:album");
        String length = getDuration(metadata);


        MetaDataDto metaDataDto = MetaDataDto.newBuilder()
                .setName(name)
                .build();

        Builder metaDataDtoBuilder = metaDataDto.toBuilder();
        if (year != null) {
            metaDataDtoBuilder.setYear(Int32Value.of(year));
        }
        if (artist != null) {
            metaDataDtoBuilder.setArtist(artist);
        }
        if (album != null) {
            metaDataDtoBuilder.setAlbum(album);
        }
        if (length != null) {
            metaDataDtoBuilder.setLength(length);
        }
        return metaDataDtoBuilder.build();
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
