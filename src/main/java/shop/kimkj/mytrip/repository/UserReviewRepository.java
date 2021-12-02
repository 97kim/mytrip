package shop.kimkj.mytrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.kimkj.mytrip.domain.UserReview;

import java.util.List;

public interface UserReviewRepository extends JpaRepository<UserReview, Long> {
//    List<UserReview> findAll(Long userId, String type);
}