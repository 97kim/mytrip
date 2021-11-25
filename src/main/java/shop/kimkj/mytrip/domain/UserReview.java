package shop.kimkj.mytrip.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.kimkj.mytrip.dto.UserReviewRequestDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserReview extends Timestamped {

    @Id
    @GeneratedValue
    @Column(name = "REVIEW_ID")
    private Long id;
    private String title;
    private String place;
    private String review;

//    @OneToMany(fetch = LAZY)
//    @JoinColumn(name = "comments_id")
//    private List<Comment> comments = new ArrayList<>();

    public UserReview(UserReviewRequestDto requestDto) {
        if (requestDto.getId() != null) {
            this.id = requestDto.getId();
        }
        this.title = requestDto.getTitle();
        this.place = requestDto.getPlace();
        this.review = requestDto.getReview();
    }
}
