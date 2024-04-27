package home.nkt.resourceservice.service.impl;

import home.nkt.generated.protobuf.MetaDataProto.MetaDataDto;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MetaDataServiceImplTest {

    private final MetaDataServiceImpl metaDataService = new MetaDataServiceImpl();

    @Test
    void getMetaData() throws IOException {
        Path path = Paths.get("src", "test", "resources", "songs", "Lesnik.mp3");
        try (InputStream is = new FileInputStream(path.toFile())) {
            byte[] bytes = is.readAllBytes();
            MetaDataDto metaData = metaDataService.getMetaData(bytes);
            Assertions.assertThat(metaData)
                    .hasFieldOrPropertyWithValue("name", "Лесник (Sefon.me)")
                    .hasFieldOrPropertyWithValue("artist", "КиШ")
                    .hasFieldOrPropertyWithValue("album", "unknown")
                    .hasFieldOrPropertyWithValue("length", "00:03:07")
                    .satisfies(data -> {
                        long year = data.getYear().getValue();
                        Assertions.assertThat(year).isEqualTo(2010);
                    });
        }
    }
}
