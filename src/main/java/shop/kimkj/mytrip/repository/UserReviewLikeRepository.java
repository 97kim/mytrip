package shop.kimkj.mytrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.kimkj.mytrip.domain.UserReviewLikes;

public interface UserReviewLikeRepository extends JpaRepository<UserReviewLikes, Long> {
    UserReviewLikes findByUserReviewIdAndUserId(Long userReviewId, Long userId);
    void deleteByUserReviewIdAndUserId(Long userReviewId, Long userId);
}