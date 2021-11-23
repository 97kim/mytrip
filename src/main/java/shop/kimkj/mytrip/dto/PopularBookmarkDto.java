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

    private String file;

    private String username;
}
