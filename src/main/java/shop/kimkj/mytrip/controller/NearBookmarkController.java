package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.dto.NearBookmarkDto;
import shop.kimkj.mytrip.service.NearBookmarkService;

@RequiredArgsConstructor
@RestController
public class NearBookmarkController {
    private final NearBookmarkService nearBookmarkService;

    @PostMapping("/near/place/bookmark")
    public void bookmarkNear(@RequestBody NearBookmarkDto nearBookmarkDto){
       nearBookmarkService.saveNearBookmark(nearBookmarkDto);
      if (nearBookmarkDto.getAction().equals("uncheck")) {
          nearBookmarkRepository.deleteByUserId(Long userId);
      }
     else {

      }
    }
}
