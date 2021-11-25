package shop.kimkj.mytrip.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.kimkj.mytrip.dto.UserReviewRequestDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public class UserReview {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long idx;
    private String title;
    private String place;
    private String review;
    private LocalDateTime modifiedAt = LocalDateTime.now();

//    @OneToMany(fetch = LAZY)
//    @JoinColumn(name = "comments_id")
//    private List<Comment> comments = new ArrayList<>();

    public UserReview(UserReviewRequestDto requestDto) {
        if (requestDto.getIdx() != null) {
            this.idx = requestDto.getIdx();
        }
        this.title = requestDto.getTitle();
        this.place = requestDto.getPlace();
        this.review = requestDto.getReview();
    }
}
