package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/near/place/{contentId}")
    public String getNearDetailIntro(@PathVariable Long contentId) throws IOException {
        return nearService.getNearDetailIntro(contentId);
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