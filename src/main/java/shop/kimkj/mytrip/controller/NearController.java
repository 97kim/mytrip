package shop.kimkj.mytrip.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(description = "위치 기반 근처 여행지 리스트 조회", method = "GET")
    @GetMapping("/nearspots")
    public String getNearPlace(@RequestParam String lat, @RequestParam String lng) throws IOException {
        return nearService.getNearPlace(lat, lng);
    }

    @Operation(description = "근처 여행지 상세데이터 조회", method = "GET")
    @GetMapping("/nearspots/{contentId}")
    public String getNearDetailIntro(@PathVariable Long contentId) throws IOException {
        return nearService.getNearDetailIntro(contentId);
    }

    @Operation(description = "근처 여행지 리스트 필터 적용", method = "POST")
    @PostMapping("/nearspots")
    public String getNearPlaceList(@RequestBody NearDto nearDto) throws IOException {
        return nearService.getNearPlaceList(nearDto);
    }

}