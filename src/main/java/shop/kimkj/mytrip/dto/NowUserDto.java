package shop.kimkj.mytrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NowUserDto {
    @JsonProperty(value = "user_id")
    Long userId;

    String type;
}
