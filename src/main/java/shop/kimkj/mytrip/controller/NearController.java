package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import shop.kimkj.mytrip.dto.LatLngDto;
import shop.kimkj.mytrip.dto.NearDto;
import shop.kimkj.mytrip.service.NearService;


import java.io.IOException;


@RequiredArgsConstructor
@RestController
public class NearController {

    private final NearService nearService;

    @GetMapping("/nearspots")
    public String getNearPlace(@RequestParam String lat, @RequestParam String lng) throws IOException {
        return nearService.getNearPlace(lat, lng);
    }

    @GetMapping("/nearspots/{contentId}")
    public String getNearDetailIntro(@PathVariable Long contentId) throws IOException {
        return nearService.getNearDetailIntro(contentId);
    }

    @PostMapping("/nearspots")
    public String getNearPlaceList(@RequestBody NearDto nearDto) throws IOException {
        return nearService.getNearPlaceList(nearDto);
    }

    @PostMapping("/weather")
    public String getWeatherNear(@RequestBody LatLngDto latLngDto) throws IOException {
        return nearService.getWeatherNear(latLngDto);
    }
}