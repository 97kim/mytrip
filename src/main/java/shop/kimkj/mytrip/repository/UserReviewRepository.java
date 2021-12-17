package shop.kimkj.mytrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.kimkj.mytrip.domain.UserReview;

import java.util.Optional;

public interface UserReviewRepository extends JpaRepository<UserReview, Long> {
}