package shop.kimkj.mytrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class BookmarkDto {

    @JsonProperty(value = "content_id")
    private String contentId;

    private String type;

    private String action;

    private String title;

    private String address;

    @JsonProperty(value = "img_url")
    private String imgUrl;
}
