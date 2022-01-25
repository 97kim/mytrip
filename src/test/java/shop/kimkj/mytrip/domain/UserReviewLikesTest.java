package shop.kimkj.mytrip.domain;

import org.junit.jupiter.api.*;
import shop.kimkj.mytrip.dto.UserReviewDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // @Order로 순서 정하는 방법
class UserReviewLikesTest {

    private User user;
    private User user2;
    private UserReview userReview;
    private UserReview userReview2;

    @BeforeEach
    void setup() {
        user = new User("testUser", "testPassword", "testNickname", "http://placeimg.com/640/480/nature");
        UserReviewDto dto = new UserReviewDto("testTitle", "testPlace", "testReview");
        userReview = new UserReview(dto, user);

        user2 = new User("testUser2", "testPassword", "testNickname", "http://placeimg.com/640/480/nature");
        UserReviewDto dto2 = new UserReviewDto("testTitle2", "testPlace2", "testReview2");
        userReview2 = new UserReview(dto2, user2);
    }

    @Test
    @DisplayName("리뷰 좋아요")
    @Order(1)
    public void createLike() {
        // given
        // setup()

        // then
        UserReviewLikes userReviewLikes = new UserReviewLikes(userReview, user);

        // when
        assertThat(userReviewLikes.getId()).isNull();
        assertThat(userReviewLikes.getUserReview()).isEqualTo(userReview);
        assertThat(userReviewLikes.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("여러 명이 리뷰 하나 좋아요")
    @Order(2)
    public void createUsersLike() {
        // given
        UserReviewLikes userReviewLike = new UserReviewLikes(userReview, user);
        UserReviewLikes userReviewLike2 = new UserReviewLikes(userReview, user2);

        // then
        List<UserReviewLikes> userReviewLikes = new ArrayList<>();
        userReviewLikes.add(userReviewLike);
        userReviewLikes.add(userReviewLike2);

        // when
        assertThat(userReviewLikes.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("한 명이 리뷰 여러 개 좋아요")
    @Order(3)
    public void createUserLikes() {
        // given
        UserReviewLikes userReviewLike = new UserReviewLikes(userReview, user);
        UserReviewLikes userReviewLike2 = new UserReviewLikes(userReview2, user);

        // then
        List<UserReviewLikes> userReviewLikes = new ArrayList<>();
        userReviewLikes.add(userReviewLike);
        userReviewLikes.add(userReviewLike2);

        // when
        assertThat(userReviewLikes.size()).isEqualTo(2);
    }

}