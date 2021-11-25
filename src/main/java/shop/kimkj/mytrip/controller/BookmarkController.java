package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.kimkj.mytrip.domain.Bookmark;
import shop.kimkj.mytrip.dto.BookmarkDto;
import shop.kimkj.mytrip.dto.NowUserDto;
import shop.kimkj.mytrip.service.BookmarkService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/popular/place/bookmark")
    public void bookmarkPopular(@RequestBody BookmarkDto bookmarkDto) {
        if (bookmarkDto.getAction().equals("uncheck")) {
            bookmarkService.deleteBookmark(bookmarkDto.getContentId());
        } else {
            bookmarkService.saveBookmark(bookmarkDto);
        }
    }

    @PostMapping("/popular/bookmark")
    public List<Bookmark> sendPopularBookmarks(@RequestBody NowUserDto nowUserDto) {
        return bookmarkService.findBookmarks(nowUserDto.getUserId(), nowUserDto.getType());
    }

    @PostMapping("/popular/place/bookmark/{contentId}")
    public Map<String, Boolean> getPopularBookmarkStatus(@PathVariable String contentId, @RequestBody NowUserDto nowUserDto) {
        Map<String, Boolean> response = new HashMap<>();
        Bookmark bookmark = bookmarkService.checkBookmarkStatus(contentId, nowUserDto.getUserId());
        if (bookmark == null) {
            response.put("bookmarkStatus", Boolean.FALSE);
        } else {
            response.put("bookmarkStatus", Boolean.TRUE);
        }
        return response;
    }

    @PostMapping("/near/place/bookmark")
    public void bookmarkNear(@RequestBody BookmarkDto bookmarkDto) {
        if (bookmarkDto.getAction().equals("uncheck")) {
            bookmarkService.deleteBookmark(bookmarkDto.getContentId());
        } else {
            bookmarkService.saveBookmark(bookmarkDto);
        }
    }

    @PostMapping("/near/bookmark")
    public List<Bookmark> sendNearBookmarks(@RequestBody NowUserDto nowUserDto) {
        return bookmarkService.findBookmarks(nowUserDto.getUserId(), nowUserDto.getType());
    }

    @PostMapping("/near/place/bookmark/{contentId}")
    public Map<String, Boolean> getNearBookmarkStatus(@PathVariable String contentId, @RequestBody NowUserDto nowUserDto) {
        Map<String, Boolean> response = new HashMap<>();
        Bookmark bookmark = bookmarkService.checkBookmarkStatus(contentId, nowUserDto.getUserId());
        if (bookmark == null) {
            response.put("bookmarkStatus", Boolean.FALSE);
        } else {
            response.put("bookmarkStatus", Boolean.TRUE);
        }
        return response;
    }


}
