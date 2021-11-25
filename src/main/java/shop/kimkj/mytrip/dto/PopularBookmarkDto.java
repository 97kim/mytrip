package shop.kimkj.mytrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PopularBookmarkDto {

    @JsonProperty(value = "content_id")
    private String contentId;

    private String action;

    private String title;

    @JsonProperty(value = "img_url")
    private String imgUrl;

    @JsonProperty(value = "user_id")
    private Long userId;
}
