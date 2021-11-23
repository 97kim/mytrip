package shop.kimkj.mytrip.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.kimkj.mytrip.dto.PopularBookmarkDto;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class PopularBookmark extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String contentId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String file;

    @Column(nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public PopularBookmark(PopularBookmarkDto popularBookmarkDto, User user) {
        this.contentId = popularBookmarkDto.getContentId();
        this.title = popularBookmarkDto.getTitle();
        this.file = popularBookmarkDto.getFile();
        this.username = popularBookmarkDto.getUsername();
        this.user = user;
    }
}
