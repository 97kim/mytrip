package shop.kimkj.mytrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PopularDto {

    private String quantity;
    private String cat1;
    private String cat2;
    private String cat3;

    @JsonProperty(value = "content_id_give") // 클라이언트에서 보내는 json 키는 스네이크 케이스라서 @JsonProperty 사용
    private String contentId;

    @JsonProperty(value = "place_lat")
    private String placeLat;

    @JsonProperty(value = "place_lng")
    private String placeLng;
}
