package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/popular/place/intro")
    public String getPopularDetailIntro(@RequestBody PopularDto popularDto) throws IOException {
        return popularService.getPopularDetailIntro(popularDto);
    }

    @PostMapping("/popular/place/weather")
    public String getWeatherPopular(@RequestBody PopularDto popularDto) throws IOException {
        return popularService.getWeatherPopular(popularDto);
    }
}
