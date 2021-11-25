package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.kimkj.mytrip.dto.ContentIdDto;
import shop.kimkj.mytrip.dto.LatLngDto;
import shop.kimkj.mytrip.dto.PopularDto;
import shop.kimkj.mytrip.service.PopularService;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PopularController {

    private final PopularService popularService;

    @PostMapping("/popular/trips")
    public String getPopularPlace() throws IOException{
        return popularService.getRandomType();
    }

    @PostMapping("/popular/list")
    public String getPopularPlaceList(@RequestBody PopularDto popularDto) throws IOException {
        return popularService.getPopularPlaceList(popularDto);
    }

    @GetMapping("/popular/place/intro/{contentId}")
    public String getPopularDetailIntro(@PathVariable String contentId) throws IOException {
        return popularService.getPopularDetailIntro(contentId);
    }

    @PostMapping("/popular/place/weather")
    public String getWeatherPopular(@RequestBody LatLngDto latLngDto) throws IOException {
        return popularService.getWeatherPopular(latLngDto);
    }
}
