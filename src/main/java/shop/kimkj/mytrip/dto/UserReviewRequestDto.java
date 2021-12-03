package shop.kimkj.mytrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;


@Getter
public class UserReviewRequestDto {
    private Long id;
    private String title;
    private String place;
    private String review;
}
