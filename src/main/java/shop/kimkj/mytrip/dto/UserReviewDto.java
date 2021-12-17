package shop.kimkj.mytrip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class UserReviewDto {
    private String title;
    private String place;
    private String review;
}
