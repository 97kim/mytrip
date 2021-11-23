package shop.kimkj.mytrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BookmarkNearDto {
    @JsonProperty(value = "content_id_give")
    private String contentId;

    @JsonProperty(value = "action_give")
    private String action;

    @JsonProperty(value = "title_give")
    private String title;

    @JsonProperty(value = "address_give")
    private String address;

    @JsonProperty(value = "file_give")
    private String file;
}
