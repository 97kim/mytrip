package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.kimkj.mytrip.dto.PopularBookmarkDto;
import shop.kimkj.mytrip.service.PopularBookmarkService;

@RequiredArgsConstructor
@RestController
public class PopularBookmarkController {
    private final PopularBookmarkService popularBookmarkService;

    @PostMapping("/popular/place/bookmark")
    public void bookmarkPopular(@RequestBody PopularBookmarkDto popularBookmarkDto) {
        popularBookmarkService.savePopularBookmark(popularBookmarkDto);
    }
}
