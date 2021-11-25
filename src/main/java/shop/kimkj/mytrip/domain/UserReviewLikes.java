package shop.kimkj.mytrip.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class UserReviewLikes extends UserReview {

//    좋아요 숫자 확인 기능 구현 예정(HJ 11.22)
    private int likes;

}
