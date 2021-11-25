package shop.kimkj.mytrip.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import shop.kimkj.mytrip.domain.UserReview;
import shop.kimkj.mytrip.dto.UserReviewCommentRequestDto;
import shop.kimkj.mytrip.dto.UserReviewRequestDto;
import shop.kimkj.mytrip.repository.UserReviewRepository;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserReviewService {

    private final UserReviewRepository userReviewRepository;

    @Transactional
    public UserReview postUserReview(UserReviewRequestDto userReviewRequestDto) {
        UserReview userReview = new UserReview(userReviewRequestDto);
        userReviewRepository.save(userReview);
        return userReview;
    }

    public UserReview getUserReview(int reviewId) {
        return userReviewRepository.findById((long)reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다.")
        );
    }

    public List<UserReview> getUserReviews() {
        return userReviewRepository.findAll();
    }

    @Transactional
    public String deleteUserReview(int reviewId) {
        userReviewRepository.deleteById((long)reviewId);
        return "삭제를 완료했습니다.";
    }

//    public UserReview postUserReviewComment(@PathVariable Long id, @RequestBody UserReviewCommentRequestDto userReviewCommentRequestDto) {
//        Comment userReview = new Comment(userReviewCommentRequestDto);
//        userReviewRepository.save(userReview);
//        return userReview;
//    }
}
