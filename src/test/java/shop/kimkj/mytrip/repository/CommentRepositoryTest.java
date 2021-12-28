package shop.kimkj.mytrip.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.kimkj.mytrip.domain.Comment;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.domain.UserReview;
import shop.kimkj.mytrip.dto.CommentDto;
import shop.kimkj.mytrip.dto.UserReviewDto;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    CommentDto commentDto;
    UserReviewDto userReviewDto;
    UserReview userReview;
    User user = new User("testId", "testPassword", "testNickname", "http://placeimg.com/640/480/nature");

    @Test
    @DisplayName("Comment 저장 테스트")
    public void saveComment() {
        //given
        commentDto = new CommentDto("testComment");
        userReviewDto = new UserReviewDto("testTitle", "testPlace", "testReview");
        userReview = new UserReview(userReviewDto, user);
        Comment comment = new Comment(commentDto, userReview, user);

        //when
        Comment savedComment = commentRepository.save(comment);

        //then
        assertThat(comment).isSameAs(savedComment);
        assertThat(comment.getComment()).isEqualTo(savedComment.getComment());
        assertThat(comment.getUserReview()).isEqualTo(savedComment.getUserReview());
        assertThat(comment.getUser()).isEqualTo(savedComment.getUser());
        assertThat(commentRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("Comment 조회 테스트")
    public void findComment() {
        //given
        commentDto = new CommentDto("Comment");
        userReviewDto = new UserReviewDto("testTitle", "testPlace", "testReview");
        userReview = new UserReview(userReviewDto, user);
        Comment savedComment = commentRepository.save(new Comment(commentDto, userReview, user));

        //when
        Comment findComment = commentRepository.findById(savedComment.getId())
                .orElseThrow(() -> new NullPointerException("해당 Comment 없음"));

        //then
        assertThat(commentRepository.count()).isEqualTo(1);
        assertThat(findComment.getComment()).isEqualTo(savedComment.getComment());
    }

    @Test
    @DisplayName("Comment 수정 테스트")
    public void updateComment() {
        //given
        commentDto = new CommentDto("Comment");
        userReviewDto = new UserReviewDto("testTitle", "testPlace", "testReview");
        userReview = new UserReview(userReviewDto, user);
        Comment savedComment = commentRepository.save(new Comment(commentDto, userReview, user));

        //when
        Comment findComment = commentRepository.findById(savedComment.getId())
                .orElseThrow(() -> new NullPointerException("해당 Comment 없음"));
        findComment.setComment("testComment2");
        Comment updatedComment = commentRepository.save(findComment);

        //then
        assertThat(commentRepository.count()).isEqualTo(1);
        assertThat(findComment.getComment()).isEqualTo(updatedComment.getComment());
        assertThat(updatedComment.getComment()).isEqualTo("testComment2");

    }

    @Test
    @DisplayName("Comment 삭제 테스트")
    public void deleteComment() {
        //given
        commentDto = new CommentDto("Comment");
        userReviewDto = new UserReviewDto("testTitle", "testPlace", "testReview");
        userReview = new UserReview(userReviewDto, user);
        Comment savedComment = commentRepository.save(new Comment(commentDto, userReview, user));

        //when
        commentRepository.deleteById(savedComment.getId());

        //then
        assertThat(commentRepository.count()).isEqualTo(0);
    }

}