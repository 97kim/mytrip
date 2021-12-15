package shop.kimkj.mytrip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserReviewRequestDto {
    private String title;
    private String place;
    private String review;
}
