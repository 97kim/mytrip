package shop.kimkj.mytrip.service;

import org.junit.jupiter.api.Assertions;
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
import shop.kimkj.mytrip.repository.UserRepository;
import shop.kimkj.mytrip.repository.UserReviewRepository;
import shop.kimkj.mytrip.security.UserDetailsImpl;

import java.io.FileInputStream;
import java.io.IOException;

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
    UserRepository userRepository;

    @Test
    @DisplayName("리뷰 생성 성공")
    void postUserReview() throws IOException {
        // given
        UserReviewDto userReviewDto = new UserReviewDto("title", "place", "review");
        MockMultipartFile multipartFile = new MockMultipartFile("image",
                "test.png",
                "image/png",
                new FileInputStream("C:\\Users\\wkdgy\\OneDrive\\바탕 화면\\Summer_beach.jpg"));
        // 테스트 실행 시 new FileInputStream = 내 로컬에 저장된 이미지 url 변경 삽입

        UserDto userDto = new UserDto("signup", "test1234", "test1234");
        User user = userService.registerUser(userDto);
        UserDetailsImpl nowUser = new UserDetailsImpl(user);

        // when
        UserReview userReview = userReviewService.postUserReview(userReviewDto, multipartFile, nowUser);
        UserReview userReviewTest = userReviewRepository.getById(userReview.getId());
        // then
        assertEquals("리뷰 Id 값이 같아야 한다.", userReview.getId(), userReviewTest.getId());
    }

    @Test
    @DisplayName("리뷰 수정 성공")
    void putUserReviewTest() throws IOException {

        UserReviewDto userReviewDto = new UserReviewDto("title", "place", "review");
        MockMultipartFile multipartFile = new MockMultipartFile("image",
                "testEdit.png",
                "image/png",
                new FileInputStream("C:\\Users\\wkdgy\\OneDrive\\바탕 화면\\Summer_beach.jpg"));
        // 테스트 실행 시 new FileInputStream = 내 로컬에 저장된 이미지 url 변경 삽입

        UserDto userDto = new UserDto("signup", "test1234", "test1234");
        User user = userService.registerUser(userDto);
        UserDetailsImpl nowUser = new UserDetailsImpl(user);

        UserReview userReview = userReviewService.postUserReview(userReviewDto, multipartFile, nowUser);
        userReviewRepository.save(userReview);

        // given
        UserReviewDto userReviewDtoEdit = new UserReviewDto("title-edit", "place-edit", "review-edit");
        MockMultipartFile multipartFileEdit = new MockMultipartFile("image",
                "test.png",
                "image/png",
                new FileInputStream("C:\\Users\\wkdgy\\OneDrive\\바탕 화면\\22.jpg"));
        // when
        UserReview userReviewEdit = userReviewService.putUserReview(userReview.getId(), userReviewDtoEdit, multipartFileEdit, nowUser);
        // then
        assertEquals("title 의 값이 변경되어야 한다.", "title-edit", userReviewEdit.getTitle());
        assertEquals("place 의 값이 변경되어야 한다.", "place-edit", userReviewEdit.getPlace());
        assertEquals("review 의 값이 변경되어야 한다.", "review-edit", userReviewEdit.getReview());
        System.out.println("image 의 값이 변경되어야 한다. " + userReviewEdit.getReviewImgUrl());
    }
}

