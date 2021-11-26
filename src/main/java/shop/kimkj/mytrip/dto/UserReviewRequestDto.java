package shop.kimkj.mytrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
public class UserReviewRequestDto {
    private Long id;
    private String title;
    private String place;
    private String review;

    @JsonProperty(value = "user_id")
    private Long userId;
}
