package shop.kimkj.mytrip.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(description = "테마별 인기 여행지 즐겨찾기", method = "POST")
    @PostMapping("/themes/{contentId}/bookmark")
    public void bookmarkPopular(@PathVariable Long contentId, @RequestBody BookmarkDto bookmarkDto, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        bookmarkService.saveBookmark(contentId, "popular", bookmarkDto, nowUser);
    }

    @Operation(description = "테마별 인기 여행지 즐겨찾기 해제", method = "DELETE")
    @DeleteMapping("/themes/{contentId}/bookmark")
    public void unBookmarkPopular(@PathVariable Long contentId, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        bookmarkService.deleteBookmark(contentId, nowUser);
    }

    @Operation(description = "테마별 인기 여행지 즐겨찾기 여부 확인", method = "GET")
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

    @Operation(description = "근처 여행지 즐겨찾기", method = "POST")
    @PostMapping("/nearspots/{contentId}/bookmark")
    public void bookmarkNear(@PathVariable Long contentId, @RequestBody BookmarkDto bookmarkDto, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        bookmarkService.saveBookmark(contentId, "near", bookmarkDto, nowUser);
    }

    @Operation(description = "근처 여행지 즐겨찾기 해제", method = "DELETE")
    @DeleteMapping("/nearspots/{contentId}/bookmark")
    public void unBookmarkNear(@PathVariable Long contentId, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        bookmarkService.deleteBookmark(contentId, nowUser);
    }

    @Operation(description = "근처 여행지 즐겨찾기 여부 확인", method = "GET")
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