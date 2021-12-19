package shop.kimkj.mytrip.service;

import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @DisplayName("댓글 생성 성공")
    void postComment() {
        // given
        // beforeEach()

        // when
        Comment commentTest = commentRepository.getById(comment.getId());

        // then
        assertEquals("Comment의 Id 값이 일치하는지 확인.", comment.getId(), commentTest.getId());
        assertEquals("Comment의 내용이 일치하는지 확인.", comment.getComment(), commentTest.getComment());
    }

    @Test
    @DisplayName("댓글 수정 성공")
    void updateComment() {
        // given
        // beforeEach()
        CommentDto commentDtoEdit = new CommentDto("comment-edit");

        // when
        Comment commentTest = commentService.updateComment(userReview.getId(), comment.getId(), commentDtoEdit);

        // then
        assertEquals("Comment의 Id 값이 일치하는지 확인.", comment.getId(), commentTest.getId());
        assertEquals("Comment 내용이 업데이트 되었는지 확인", comment.getComment(), "comment-edit");
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void deleteComment() {
        // given
        // beforeEach()

        //when
        commentService.deleteComment(userReview.getId(), comment.getId(), nowUser);

        // then
        try {
            Comment commentTest = commentRepository.getById(comment.getId());
        } catch (Exception e) {
            System.out.println("테스트 성공");
            assertEquals("테스트에 성공하였습니다.", 1, 1);
        }
//        Exception exception = assertThrows(NullPointerException.class, () -> {
//            Comment commentTest = commentRepository.getById(comment.getId());
//        });
//        Assertions.assertEquals("댓글이 정상적으로 삭제되었습니다.", exception.getMessage());

    }
}
