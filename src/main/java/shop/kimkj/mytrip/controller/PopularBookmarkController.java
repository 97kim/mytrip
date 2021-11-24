package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.kimkj.mytrip.domain.PopularBookmark;
import shop.kimkj.mytrip.dto.NowUserDto;
import shop.kimkj.mytrip.dto.PopularBookmarkDto;
import shop.kimkj.mytrip.service.PopularBookmarkService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PopularBookmarkController {
    private final PopularBookmarkService popularBookmarkService;

    @PostMapping("/popular/place/bookmark")
    public void bookmarkPopular(@RequestBody PopularBookmarkDto popularBookmarkDto) {
        if (popularBookmarkDto.getAction().equals("uncheck")) {
            popularBookmarkService.deletePopularBookmark(popularBookmarkDto.getContentId());
        } else {
            popularBookmarkService.savePopularBookmark(popularBookmarkDto);
        }
    }

    @PostMapping("/popular/bookmark")
    public List<PopularBookmark> sendPopularBookmarks(@RequestBody NowUserDto nowUserDto) {
        return popularBookmarkService.findBookmarks(nowUserDto.getUsername());
    }

    @PostMapping("/popular/place/bookmark/{contentId}")
    public Map<String, Boolean> getPopularBookmarkStatus(@PathVariable String contentId, @RequestBody NowUserDto nowUserDto) {
        Map<String, Boolean> response = new HashMap<>();
        PopularBookmark popularBookmark = popularBookmarkService.checkPopularBookmarkStatus(contentId, nowUserDto.getUsername());
        if (popularBookmark == null) {
            response.put("bookmarkStatus", Boolean.FALSE);
        } else {
            response.put("bookmarkStatus", Boolean.TRUE);
        }
        return response;
    }
}
