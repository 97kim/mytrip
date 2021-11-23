package shop.kimkj.mytrip.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Comment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long idx;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = LAZY)
    private UserReview userReview;

    public Comment(String comment) {
        this.comment = comment;
    }
}