package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.kimkj.mytrip.domain.UserReview;
import shop.kimkj.mytrip.dto.UserReviewRequestDto;
import shop.kimkj.mytrip.service.UserReviewService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserReviewController {

    private final UserReviewService userReviewService;

    @PostMapping("/userReview")
    public UserReview postUserReview(@RequestBody UserReviewRequestDto userReviewRequestDto) {
        return userReviewService.postUserReview(userReviewRequestDto);
    }

    @GetMapping("/userReview")
    public UserReview getUserReview(@RequestParam(name = "id") int reviewId) {
        return userReviewService.getUserReview(reviewId);
    }

    @GetMapping("/userReviews")
    public List<UserReview> getUserReviews() {
        return userReviewService.getUserReviews();
    }

    @DeleteMapping("/userReview/trip/delete")
    public String deleteUserReview(@RequestParam(name = "id") int reviewId) {
        return userReviewService.deleteUserReview(reviewId);
    }

//    @PostMapping("/userReview/{id}")
//    public UserReview postUserReviewComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
//        return userReviewService.postUserReviewComment(comment);
//    }

}
