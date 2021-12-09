package shop.kimkj.mytrip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.kimkj.mytrip.dto.BookmarkDto;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Bookmark extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "BOOKMARK_ID")
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Long contentId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String imgUrl;

    @Column
    private String address;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public Bookmark(Long contentId, String type, BookmarkDto bookmarkDto, User user) {
        this.contentId = contentId;
        this.type = type;
        this.title = bookmarkDto.getTitle();
        this.imgUrl = bookmarkDto.getImgUrl();
        this.address = bookmarkDto.getAddress();
        this.user = user;
    }
}