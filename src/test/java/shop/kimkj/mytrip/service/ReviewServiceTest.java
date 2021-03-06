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
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.domain.UserReview;
import shop.kimkj.mytrip.dto.UserDto;
import shop.kimkj.mytrip.dto.UserReviewDto;
import shop.kimkj.mytrip.repository.UserReviewLikeRepository;
import shop.kimkj.mytrip.repository.UserReviewRepository;
import shop.kimkj.mytrip.security.UserDetailsImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertEquals;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
public class ReviewServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserReviewService userReviewService;
    @Autowired
    UserReviewRepository userReviewRepository;
    @Autowired
    UserReviewLikeRepository userReviewLikeRepository;

    UserDetailsImpl nowUser;
    UserReviewDto userReviewDto;
    MockMultipartFile multipartFile;

    // 테스트 사진 1 - 사진 URL 입력해주세요.
    String photo = "C:\\Users\\wkdgy\\OneDrive\\바탕 화면\\Summer_beach.jpg";
    // 테스트 사진 2 - 테스트 사진 1과 다른 비교할 사진의 URL 이 필요합니다.
    String photo2 = "C:\\Users\\wkdgy\\OneDrive\\바탕 화면\\22.jpg";

    @BeforeEach
    void beforeEach() throws IOException {
        userReviewRepository.deleteAll();
        UserDto userDto = new UserDto("test1234", "test1234");

        User user = userService.registerUser(userDto);
        this.nowUser = new UserDetailsImpl(user);
        this.userReviewDto = new UserReviewDto("title", "place", "review");
        this.multipartFile = new MockMultipartFile("image",
                "testPhoto.png",
                "image/png",
                new FileInputStream(photo));
    }

    @Test
    @DisplayName("리뷰 생성 성공")
    void postUserReview() throws IOException {
        // given
        UserReview userReview = userReviewService.postUserReview(userReviewDto, multipartFile, nowUser);

        // when
        UserReview userReviewTest = userReviewRepository.findById(userReview.getId()).orElseThrow(
                () -> new NullPointerException("Review 가 정상적으로 생성되지 않았습니다.")
        );

        // then
        assertEquals("리뷰 Id 값이 같아야 한다.", userReview.getId(), userReviewTest.getId());
    }

    @Test
    @DisplayName("리뷰 수정 성공")
    void putUserReviewTest() throws IOException {
        // given
        UserReview userReview = userReviewService.postUserReview(userReviewDto, multipartFile, nowUser);
        userReviewRepository.save(userReview);

        UserReviewDto userReviewDtoEdit = new UserReviewDto("title-edit", "place-edit", "review-edit");
        MockMultipartFile multipartFileEdit = new MockMultipartFile("image",
                "testPhotoEdit.png",
                "image/png",
                new FileInputStream(photo2));

        // when
        UserReview userReviewEdit = userReviewService.putUserReview(userReview.getId(), userReviewDtoEdit, multipartFileEdit, nowUser);

        // then
        assertEquals("title 의 값이 변경되어야 한다.", userReviewDtoEdit.getTitle(), userReviewEdit.getTitle());
        assertEquals("place 의 값이 변경되어야 한다.", userReviewDtoEdit.getPlace(), userReviewEdit.getPlace());
        assertEquals("review 의 값이 변경되어야 한다.", userReviewDtoEdit.getReview(), userReviewEdit.getReview());
        System.out.println("image 의 값이 변경되어야 한다. " + userReviewEdit.getReviewImgUrl());
    }

    @Test
    @DisplayName("리뷰 삭제 성공")
    void deleteUserReview() throws IOException {
        // given
        UserReview userReview = userReviewService.postUserReview(userReviewDto, multipartFile, nowUser);

        // when
        userReviewService.deleteUserReview(userReview.getId(), nowUser);

        // then
        Optional<UserReview> userReviewTest = userReviewRepository.findById(userReview.getId());

        if (userReviewTest.isPresent())
            throw new IllegalArgumentException("UserReview 가 정상적으로 삭제되지 않았습니다.");
        else
            assertEquals("Comment 가 정상적으로 삭제되었습니다.", Optional.empty(), userReviewTest);
    }

    @Test
    @DisplayName("좋아요 성공")
    void saveLike() throws IOException {
        // given
        UserReview userReview = userReviewService.postUserReview(userReviewDto, multipartFile, nowUser);

        // when
        userReviewService.saveLike(userReview.getId(), nowUser);

        // then
        assertEquals("UserReview.getLikeCnt 값이 1이 되어야 한다.", 1, userReview.getLikeCnt());
    }

    @Test
    @DisplayName("좋아요 삭제")
    void deleteLike() throws IOException {
        // given
        UserReview userReview = userReviewService.postUserReview(userReviewDto, multipartFile, nowUser);
        userReviewService.saveLike(userReview.getId(), nowUser);
        assertEquals("UserReview.getLikeCnt 값이 1이 되어야 한다.", 1, userReview.getLikeCnt());

        // when
        userReviewService.deleteLike(userReview.getId(), nowUser);

        // then
        assertEquals("UserReview.getLikeCnt 값이 0이 되어야 한다.", 0, userReview.getLikeCnt());
    }
}

