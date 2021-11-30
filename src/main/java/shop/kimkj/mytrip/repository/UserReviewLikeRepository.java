package shop.kimkj.mytrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.kimkj.mytrip.domain.UserReviewLikes;

import java.util.List;

public interface UserReviewLikeRepository extends JpaRepository<UserReviewLikes, Long> {
    List<UserReviewLikes> findAllByUserReviewId(Long userReviewId);
    UserReviewLikes findByUserReviewIdAndUserId(Long userReviewId, Long userId);
    void deleteByUserReviewId(Long userReviewId);
}
