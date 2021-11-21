package shop.kimkj.mytrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PopularDto {

    private String quantity;
    private String cat1;
    private String cat2;
    private String cat3;

}
