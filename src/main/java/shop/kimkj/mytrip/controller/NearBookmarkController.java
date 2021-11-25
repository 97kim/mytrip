package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.kimkj.mytrip.domain.NearBookmark;
import shop.kimkj.mytrip.dto.NearBookmarkDto;
import shop.kimkj.mytrip.dto.NowUserDto;
import shop.kimkj.mytrip.service.NearBookmarkService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class NearBookmarkController {
    private final NearBookmarkService nearBookmarkService;

    @PostMapping("/near/place/bookmark")
    public void bookmarkNear(@RequestBody NearBookmarkDto nearBookmarkDto){

      if (nearBookmarkDto.getAction().equals("uncheck")) {
          nearBookmarkService.deleteNearBookmark(nearBookmarkDto.getContentId());
      }
     else {
         nearBookmarkService.saveNearBookmark(nearBookmarkDto);
      }
    }

    @PostMapping("/near/bookmark")
    public List<NearBookmark> sendNearBookmarks(@RequestBody NowUserDto nowUserDto) {
        return nearBookmarkService.findBookmarks(nowUserDto.getUserId());
    }

    @PostMapping("/near/place/bookmark/{contentId}")
    public Map<String, Boolean> getNearBookmarkStatus(@PathVariable String contentId, @RequestBody NowUserDto nowUserDto) {
        Map<String, Boolean> response = new HashMap<>();
        NearBookmark nearBookmark = nearBookmarkService.checkNearBookmarkStatus(contentId, nowUserDto.getUserId());
        if (nearBookmark == null) {
            response.put("bookmarkStatus", Boolean.FALSE);
        } else {
            response.put("bookmarkStatus", Boolean.TRUE);
        }
        return response;
    }
}
