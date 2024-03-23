package home.nkt.resourceservice.service.dto;

import lombok.Data;

@Data
public class MetaDataDto {

    private String name;
    private String artist;
    private String album;
    private String length;
    private Long resourceId;
    private Integer year;
}
