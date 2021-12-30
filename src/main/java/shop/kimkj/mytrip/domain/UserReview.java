package shop.kimkj.mytrip.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.kimkj.mytrip.dto.UserReviewDto;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserReview extends Timestamped {

    @Id
    @GeneratedValue
    @Column(name = "REVIEW_ID")
    private Long id;

    @Column
    private String title;

    @Column
    private String place;

    @Column
    private String review;

    @Column
    private String reviewImgUrl;

    @Column
    private int likeCnt;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "userReview", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "userReview", cascade = CascadeType.REMOVE)
    private List<UserReviewLikes> userReviewLikes;

    public UserReview(UserReviewDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.place = requestDto.getPlace();
        this.review = requestDto.getReview();
        this.user = user;
    }

}