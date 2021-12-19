package shop.kimkj.mytrip.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.kimkj.mytrip.domain.Comment;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.domain.UserReview;
import shop.kimkj.mytrip.dto.CommentDto;
import shop.kimkj.mytrip.dto.UserDto;
import shop.kimkj.mytrip.dto.UserReviewDto;
import shop.kimkj.mytrip.repository.CommentRepository;
import shop.kimkj.mytrip.repository.UserReviewRepository;
import shop.kimkj.mytrip.security.UserDetailsImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
public class BookmarkServiceTest {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserReviewRepository userReviewRepository;
    @Autowired
    UserService userService;

    User user;
    UserDetailsImpl nowUser;
    UserReview userReview;
    Comment comment;

    @BeforeEach
    void beforeEach() {
        UserReviewDto userReviewDto = new UserReviewDto("title", "place", "review");

        UserDto userDto = new UserDto("signup", "test1234", "test1234");
        this.user = userService.registerUser(userDto);
        this.nowUser = new UserDetailsImpl(user);

        UserReview saveUserReview = new UserReview(userReviewDto, user);
        this.userReview = userReviewRepository.save(saveUserReview);

        CommentDto commentDto = new CommentDto("comment");

        this.comment = commentService.postComment(userReview.getId(), commentDto, nowUser);
    }

    @Test
    @DisplayName("북마크 저장 성공")
    void saveBookmark() {

    }

    @Test
    @DisplayName("북마스 삭제 성공")
    void deleteBookmark() {

    }

    @Test
    @DisplayName("북마스 상태 확인 성공")
    void checkBookmarkStatus() {

    }
}
