package shop.kimkj.mytrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LatLngDto {

    @JsonProperty(value = "place_lat")
    private String placeLat;

    @JsonProperty(value = "place_lng")
    private String placeLng;
}
