package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.kimkj.mytrip.domain.UserReview;
import shop.kimkj.mytrip.domain.UserReviewLikes;
import shop.kimkj.mytrip.dto.UserReviewLikeDto;
import shop.kimkj.mytrip.dto.UserReviewRequestDto;
import shop.kimkj.mytrip.security.UserDetailsImpl;
import shop.kimkj.mytrip.service.UserReviewService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserReviewController {

    private final UserReviewService userReviewService;


    @PostMapping("/review")
    public ResponseEntity<?> postUserReview(@RequestPart(name = "review_data") UserReviewRequestDto userReviewRequestDto,
                                            @RequestPart(name = "review_img", required = false) MultipartFile multipartFile,
                                            @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {
        return userReviewService.postUserReview(userReviewRequestDto, multipartFile, nowUser);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<?> putUserReview(@PathVariable Long reviewId,
                              @RequestPart(name = "review_data") UserReviewRequestDto userReviewRequestDto,
                              @RequestPart(name = "review_img", required = false) MultipartFile multipartFile,
                              @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {
        return userReviewService.putUserReview(reviewId, userReviewRequestDto, multipartFile, nowUser);
    }


    @GetMapping("/reviews/{reviewId}")
    public UserReview getUserReview(@PathVariable Long reviewId) {
        return userReviewService.getUserReview(reviewId);
    }

    @GetMapping("/reviews") // 타입 별(최근, 좋아요) 순서대로 받아오기
    public List<UserReview> getUserReviews(@RequestParam String sort) throws Exception {
        return userReviewService.getUserReviews(sort);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteUserReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl nowUser) { // @AuthenticationPrincipal 로그인한 유저 정보 가져오기
        return userReviewService.deleteUserReview(reviewId, nowUser);
    }

    @PostMapping("/review/like") // 눌러서 언체크면 삭제하고 아니면 save
    public void userReviewLike(@RequestBody UserReviewLikeDto userReviewLikeDto, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        if (userReviewLikeDto.getAction().equals("uncheck")) {
            userReviewService.deleteLike(userReviewLikeDto.getUserReviewId(), nowUser);
        } else {
            userReviewService.saveLike(userReviewLikeDto.getUserReviewId(), nowUser);
        }
    }

    @GetMapping("/review/like/{userReviewId}") // 좋아요 된 게시물은 나갔다 들어와도 좋아요 된 것으로 표시
    public Map<String, Boolean> getLikeStatus(@PathVariable Long userReviewId, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        Map<String, Boolean> response = new HashMap<>();
        UserReviewLikes userReviewLikes = userReviewService.checkLikeStatus(userReviewId, nowUser.getId());
        if (userReviewLikes == null) {
            response.put("likeStatus", Boolean.FALSE);
        } else {
            response.put("likeStatus", Boolean.TRUE);
        }
        return response;
    }
}