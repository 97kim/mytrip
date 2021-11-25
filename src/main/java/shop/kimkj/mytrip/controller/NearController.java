package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.kimkj.mytrip.dto.ContentIdDto;
import shop.kimkj.mytrip.dto.LatLngDto;
import shop.kimkj.mytrip.dto.NearDto;
import shop.kimkj.mytrip.service.NearService;


import java.io.IOException;


@RequiredArgsConstructor
@RestController
public class NearController {

    private final NearService nearService;

    @PostMapping("/near")
    public String getNearPlace(@RequestBody LatLngDto latLngDto) throws IOException {
        return nearService.getNearPlace(latLngDto);
    }

    @PostMapping("/near/place/intro")
    public String getNearDetailIntro(@RequestBody ContentIdDto contentIdDto) throws IOException {
        return nearService.getNearDetailIntro(contentIdDto);
    }

    @PostMapping("/near/list")
    public String getNearPlaceList(@RequestBody NearDto nearDto) throws IOException {
        return nearService.getNearPlaceList(nearDto);
    }

    @PostMapping("/near/place/weather")
    public String getWeatherNear(@RequestBody LatLngDto latLngDto) throws IOException {
        return nearService.getWeatherNear(latLngDto);
    }

}