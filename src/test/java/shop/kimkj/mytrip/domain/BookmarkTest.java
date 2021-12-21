package shop.kimkj.mytrip.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import shop.kimkj.mytrip.dto.BookmarkDto;
import shop.kimkj.mytrip.dto.UserReviewDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // @Order로 순서 정하는 방법
class BookmarkTest {

    private User user;

    @BeforeEach
    void setup() {
        user = new User("testUser", "testPassword", "testNickname", "http://placeimg.com/640/480/nature");
    }

    @Test
    @DisplayName("NearSpot 즐겨찾기")
    @Order(1)
    public void createNearSpotBookmark() {
        //given
        BookmarkDto bookmarkDto = new BookmarkDto("testTitle", "testAddress", "http://placeimg.com/640/480/nature");

        //when
        Bookmark bookmark = new Bookmark(2500296L, "near", bookmarkDto, user);

        //then
        assertThat(bookmark.getId()).isNull();
        assertThat(bookmark.getContentId()).isEqualTo(2500296L);
        assertThat(bookmark.getType()).isEqualTo("near");
        assertThat(bookmark.getTitle()).isEqualTo("testTitle");
        assertThat(bookmark.getAddress()).isEqualTo("testAddress");
        assertThat(bookmark.getImgUrl()).isEqualTo("http://placeimg.com/640/480/nature");
        assertThat(bookmark.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("Theme 즐겨찾기")
    @Order(2)
    public void createThemeBookmark() {
        //given
        BookmarkDto bookmarkDto = new BookmarkDto("testTitle", null, "http://placeimg.com/640/480/nature");
        bookmarkDto.setTitle("testTitle");

        //when
        Bookmark bookmark = new Bookmark(1904557L, "popular", bookmarkDto, user);

        //then
        assertThat(bookmark.getId()).isNull();
        assertThat(bookmark.getContentId()).isEqualTo(1904557L);
        assertThat(bookmark.getType()).isEqualTo("popular");
        assertThat(bookmark.getTitle()).isEqualTo("testTitle");
        assertThat(bookmark.getAddress()).isNull();
        assertThat(bookmark.getImgUrl()).isEqualTo("http://placeimg.com/640/480/nature");
        assertThat(bookmark.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("즐겨찾기 여러 개 생성")
    @Order(3)
    public void createBookmarks() {
        //given
        BookmarkDto bookmarkDto = new BookmarkDto("testTitle", "testAddress", "http://placeimg.com/640/480/nature");
        BookmarkDto bookmarkDto2 = new BookmarkDto("testTitle2", "testAddress2", "http://placeimg.com/640/480/nature");
        BookmarkDto bookmarkDto3 = new BookmarkDto("testTitle3", null, "http://placeimg.com/640/480/nature");
        Bookmark bookmark = new Bookmark(2500296L, "near", bookmarkDto, user);
        Bookmark bookmark2 = new Bookmark(2760891L, "near", bookmarkDto2, user);
        Bookmark bookmark3 = new Bookmark(1904557L, "popular", bookmarkDto3, user);

        //when
        List<Bookmark> bookmarks = new ArrayList<>();
        bookmarks.add(bookmark);
        bookmarks.add(bookmark2);
        bookmarks.add(bookmark3);

        //then
        assertThat(bookmarks.size()).isEqualTo(3);
    }
}