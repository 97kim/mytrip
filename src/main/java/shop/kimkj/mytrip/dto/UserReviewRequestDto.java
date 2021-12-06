package shop.kimkj.mytrip.dto;

import lombok.Getter;

@Getter
public class UserReviewRequestDto {
    private Long id;
    private String title;
    private String place;
    private String review;
}
