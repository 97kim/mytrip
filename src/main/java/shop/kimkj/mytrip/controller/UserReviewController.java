package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.kimkj.mytrip.domain.UserReview;
import shop.kimkj.mytrip.dto.UserReviewRequestDto;
import shop.kimkj.mytrip.security.UserDetailsImpl;
import shop.kimkj.mytrip.service.UserReviewService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserReviewController {

    private final UserReviewService userReviewService;

    @PostMapping("/userReview")
    public ResponseEntity<?> postUserReview(@RequestPart(name = "review_data") UserReviewRequestDto userReviewRequestDto,
                                            @RequestPart(name = "review_img", required = false) MultipartFile multipartFile,
                                            @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {
        return userReviewService.postUserReview(userReviewRequestDto, multipartFile, nowUser);
    }

    @GetMapping("/userReview/{reviewId}")
    public UserReview getUserReview(@PathVariable Long reviewId) {
        return userReviewService.getUserReview(reviewId);
    }

    @GetMapping("/userReviews")
    public List<UserReview> getUserReviews() {
        return userReviewService.getUserReviews();
    }

    @DeleteMapping("/userReview/delete/{reviewId}")
    public ResponseEntity<?> deleteUserReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl nowUser) { // @AuthenticationPrincipal 로그인한 유저 정보 가져오기
        return userReviewService.deleteUserReview(reviewId, nowUser);
    }

}
