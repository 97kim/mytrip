package shop.kimkj.mytrip.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import shop.kimkj.mytrip.domain.Bookmark;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.domain.UserReview;
import shop.kimkj.mytrip.dto.BookmarkDto;
import shop.kimkj.mytrip.dto.UserDto;
import shop.kimkj.mytrip.dto.UserReviewDto;
import shop.kimkj.mytrip.repository.BookmarkRepository;
import shop.kimkj.mytrip.security.UserDetailsImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
public class BookmarkServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    BookmarkService bookmarkService;
    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    UserReviewService userReviewService;

    User user;
    UserDetailsImpl nowUser;
    UserReviewDto userReviewDto;
    MockMultipartFile multipartFile;
    UserReview userReview;

    @BeforeEach
    void beforeEach() throws IOException {
        UserDto userDto = new UserDto();
        userDto.setUsername("test1234");
        userDto.setPassword("test1234");

        this.user = userService.registerUser(userDto);
        this.nowUser = new UserDetailsImpl(user);
        this.userReviewDto = new UserReviewDto("title", "place", "review");
        this.multipartFile = new MockMultipartFile("image",
                "testEdit.png",
                "image/png",
                new FileInputStream("C:\\Users\\wkdgy\\OneDrive\\바탕 화면\\Summer_beach.jpg"));
        this.userReview = userReviewService.postUserReview(userReviewDto, multipartFile, nowUser);
        // 테스트 실행 시 new FileInputStream = 내 로컬에 저장된 이미지 url 변경
    }

    @Test
    @DisplayName("북마크 저장 성공")
    void saveBookmark() {
        // given
        BookmarkDto bookmarkDto = new BookmarkDto("title", "address", "imgUrl");
        Bookmark bookmark = bookmarkService.saveBookmark(userReview.getId(), "popularTest", bookmarkDto, nowUser);

        // when
        Bookmark bookmarkTest = bookmarkRepository.findById(bookmark.getId()).orElseThrow(
                () -> new NullPointerException("Bookmark 가 정상적으로 생성되지 않았습니다.")
        );

        // then
        assertEquals("북마크가 성공적으로 저장되었습니다.", userReview.getId(), bookmarkTest.getContentId());
    }

    @Test
    @DisplayName("북마크 삭제 성공")
    void deleteBookmark() {
        // given
        BookmarkDto bookmarkDto = new BookmarkDto("title", "address", "imgUrl");
        Bookmark bookmark = bookmarkService.saveBookmark(userReview.getId(), "nearTest", bookmarkDto, nowUser);

        bookmarkService.deleteBookmark(userReview.getId(), nowUser);

        // when
        Optional<Bookmark> bookmarkTest = bookmarkRepository.findById(bookmark.getId());

        // then
        if (bookmarkTest.isPresent())
            throw new IllegalArgumentException("북마크 삭제가 실패하였습니다.");
        else
            assertEquals("북마크가 정상적으로 삭제되었습니다.", Optional.empty(), bookmarkTest);
    }
}
