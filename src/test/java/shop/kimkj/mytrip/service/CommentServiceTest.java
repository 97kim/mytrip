package shop.kimkj.mytrip.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.domain.UserReview;
import shop.kimkj.mytrip.dto.UserDto;
import shop.kimkj.mytrip.dto.UserReviewDto;
import shop.kimkj.mytrip.repository.CommentRepository;
import shop.kimkj.mytrip.repository.UserRepository;
import shop.kimkj.mytrip.repository.UserReviewRepository;
import shop.kimkj.mytrip.security.UserDetailsImpl;

import java.io.FileInputStream;
import java.io.IOException;

import static org.springframework.test.util.AssertionErrors.assertEquals;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
public class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserReviewRepository userReviewRepository;
    @Autowired
    UserService userService;

    @Test
    @DisplayName("댓글 생성 성공")
    void postComment() throws IOException {
        // given
        UserReviewDto userReviewDto = new UserReviewDto("title", "place", "review");

        UserDto userDto = new UserDto("signup", "test1234", "test1234");
        User user = userService.registerUser(userDto);

        UserReview userReviewComment = new UserReview(userReviewDto, user);
        userReviewRepository.save(userReviewComment);
        // when
        UserReview userReviewCommentTest = userReviewRepository.getById(userReviewComment.getId());
        // then
        assertEquals("Comment의 Id 값이 같은지 확인.", userReviewComment.getId(), userReviewCommentTest.getId());
    }

    void updateComment() {

    }

    void deleteComment() {

    }

}
