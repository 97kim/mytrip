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

    @PostMapping("/themes/{contentId}/bookmark")
    public void bookmarkPopular(@PathVariable Long contentId, @RequestBody BookmarkDto bookmarkDto, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        if (bookmarkDto.getAction().equals("uncheck")) {
            bookmarkService.deleteBookmark(contentId, nowUser);
        } else {
            bookmarkService.saveBookmark(contentId, bookmarkDto, nowUser);
        }
    }

    @GetMapping("/themes/{contentId}/bookmark")
    public Map<String, Boolean> getPopularBookmarkStatus(@PathVariable Long contentId, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        Map response = new HashMap<String, Boolean>();
        Bookmark bookmark = bookmarkService.checkBookmarkStatus(contentId, nowUser.getId());
        if (bookmark == null) {
            response.put("bookmarkStatus", Boolean.FALSE);
        } else {
            response.put("bookmarkStatus", Boolean.TRUE);
        }
        return response;
    }

    @PostMapping("/nearspots/{contentId}/bookmark")
    public void bookmarkNear(@PathVariable Long contentId, @RequestBody BookmarkDto bookmarkDto, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        if (bookmarkDto.getAction().equals("uncheck")) {
            bookmarkService.deleteBookmark(contentId, nowUser);
        } else {
            bookmarkService.saveBookmark(contentId, bookmarkDto, nowUser);
        }
    }

    @GetMapping("/nearspots/{contentId}/bookmark")
    public Map<String, Boolean> getNearBookmarkStatus(@PathVariable Long contentId, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        Map<String, Boolean> response = new HashMap<>();
        Bookmark bookmark = bookmarkService.checkBookmarkStatus(contentId, nowUser.getId());
        if (bookmark == null) {
            response.put("bookmarkStatus", Boolean.FALSE);
        } else {
            response.put("bookmarkStatus", Boolean.TRUE);
        }
        return response;
    }

    @GetMapping("/bookmarks")
    public List<Bookmark> sendBookmarks(@RequestParam String type, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        return bookmarkService.findBookmarks(nowUser.getId(), type);
    }
}
