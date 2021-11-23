package shop.kimkj.mytrip.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.kimkj.mytrip.domain.UserReview;
import shop.kimkj.mytrip.dto.UserReviewRequestDto;
import shop.kimkj.mytrip.repository.UserReviewRepository;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserReviewService {

    private final UserReviewRepository userReviewRepository;

    public UserReview postUserReview(UserReviewRequestDto userReviewRequestDto) {
        UserReview userReview = new UserReview(userReviewRequestDto);
        userReviewRepository.save(userReview);
        return userReview;
    }

    public UserReview getUserReview(int id) {
        return userReviewRepository.findById((long) id).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다.")
        );
    }

    public List<UserReview> getUserReviews() {
        return userReviewRepository.findAll();
    }

    public String deleteUserReview(int id) {
        userReviewRepository.deleteById((long) id);
        return "삭제를 완료했습니다.";
    }

//    public UserReview postUserReviewComment(@PathVariable Long id,@RequestBody UserReviewCommentRequestDto userReviewCommentRequestDto) {
//        Comment userReview = new Comment(userReviewCommentRequestDto);
//        userReviewRepository.save(userReview);
//        return userReview;
//    }
}
