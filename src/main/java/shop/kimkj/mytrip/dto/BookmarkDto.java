package shop.kimkj.mytrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class BookmarkDto {

    private String title;

    private String address;

    @JsonProperty(value = "img_url")
    private String imgUrl;
}