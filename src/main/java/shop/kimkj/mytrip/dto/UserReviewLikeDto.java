package shop.kimkj.mytrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserReviewLikeDto {

    @JsonProperty(value = "user_review_id")
    private Long userReviewId;

    private String action;
}