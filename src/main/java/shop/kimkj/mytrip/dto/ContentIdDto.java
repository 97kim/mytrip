package shop.kimkj.mytrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ContentIdDto {

    @JsonProperty(value = "content_id_give") // 클라이언트에서 보내는 json 키는 스네이크 케이스라서 @JsonProperty 사용
    private String contentId;
}
