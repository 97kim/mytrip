package shop.kimkj.mytrip.domain;

import org.junit.jupiter.api.*;
import shop.kimkj.mytrip.dto.CommentDto;
import shop.kimkj.mytrip.dto.UserReviewDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // @Order로 순서 정하는 방법
class CommentTest {

    private User user;
    private UserReview userReview;

    @BeforeEach
    void setup() {
        user = new User("testUser", "testPassword", "testNickname", "http://placeimg.com/640/480/nature");
        UserReviewDto dto = new UserReviewDto("testTitle", "testPlace", "testReview");
        userReview = new UserReview(dto, user);
    }

    @Test
    @DisplayName("댓글 생성")
    @Order(1)
    public void createComment() {
        // given
        CommentDto commentDto = new CommentDto("testComment");

        // then
        Comment comment = new Comment(commentDto, userReview, user);

        // when
        assertThat(comment.getId()).isNull();
        assertThat(comment.getComment()).isEqualTo(commentDto.getComment());
        assertThat(comment.getUserReview()).isEqualTo(userReview);
        assertThat(comment.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("댓글 여러 개 생성")
    @Order(2)
    public void createComments() {
        // given
        CommentDto commentDto = new CommentDto("testComment");
        CommentDto commentDto2 = new CommentDto("testComment2");
        CommentDto commentDto3 = new CommentDto("testComment3");

        Comment comment = new Comment(commentDto, userReview, user);
        Comment comment2 = new Comment(commentDto2, userReview, user);
        Comment comment3 = new Comment(commentDto3, userReview, user);

        // then
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        comments.add(comment2);
        comments.add(comment3);

        // when
        assertThat(comments.size()).isEqualTo(3);
    }

}