package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.kimkj.mytrip.domain.Bookmark;
import shop.kimkj.mytrip.dto.BookmarkDto;
import shop.kimkj.mytrip.security.UserDetailsImpl;
import shop.kimkj.mytrip.service.BookmarkService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/theme/bookmark")
    public void bookmarkPopular(@RequestBody BookmarkDto bookmarkDto, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        if (bookmarkDto.getAction().equals("uncheck")) {
            bookmarkService.deleteBookmark(bookmarkDto.getContentId());
        } else {
            bookmarkService.saveBookmark(bookmarkDto, nowUser);
        }
    }

    @GetMapping("/theme/bookmark")
    public List<Bookmark> sendPopularBookmarks(@RequestParam String type, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        return bookmarkService.findBookmarks(nowUser.getId(), type);
    }

    @GetMapping("/theme/bookmark/{contentId}")
    public Map<String, Boolean> getPopularBookmarkStatus(@PathVariable String contentId, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        Map response = new HashMap<String, Boolean>();
        Bookmark bookmark = bookmarkService.checkBookmarkStatus(contentId, nowUser.getId());
        if (bookmark == null) {
            response.put("bookmarkStatus", Boolean.FALSE);
        } else {
            response.put("bookmarkStatus", Boolean.TRUE);
        }
        return response;
    }

    @PostMapping("/nearspot/bookmark")
    public void bookmarkNear(@RequestBody BookmarkDto bookmarkDto, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        if (bookmarkDto.getAction().equals("uncheck")) {
            bookmarkService.deleteBookmark(bookmarkDto.getContentId());
        } else {
            bookmarkService.saveBookmark(bookmarkDto, nowUser);
        }
    }

    @GetMapping("/nearspot/bookmark")
    public List<Bookmark> sendNearBookmarks(@RequestParam String type, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        return bookmarkService.findBookmarks(nowUser.getId(), type);
    }

    @GetMapping("/nearspot/bookmark/{contentId}")
    public Map<String, Boolean> getNearBookmarkStatus(@PathVariable String contentId, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        Map<String, Boolean> response = new HashMap<>();
        Bookmark bookmark = bookmarkService.checkBookmarkStatus(contentId, nowUser.getId());
        if (bookmark == null) {
            response.put("bookmarkStatus", Boolean.FALSE);
        } else {
            response.put("bookmarkStatus", Boolean.TRUE);
        }
        return response;
    }


}
