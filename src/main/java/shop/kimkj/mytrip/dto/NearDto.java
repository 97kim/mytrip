package shop.kimkj.mytrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NearDto {
    @JsonProperty(value = "lat_give")
    private String lat;

    @JsonProperty(value = "lng_give")
    private String lng;

    @JsonProperty(value = "type_give")
    private String type;

    @JsonProperty(value = "quantity_give")
    private String quantity;

}