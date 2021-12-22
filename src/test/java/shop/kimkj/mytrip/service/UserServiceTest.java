package shop.kimkj.mytrip.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.dto.UserDto;
import shop.kimkj.mytrip.repository.UserRepository;
import shop.kimkj.mytrip.security.UserDetailsImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertEquals;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    UserDto userDto;

    @BeforeEach
    void beforeEach() {
        this.userDto = new UserDto("test", "test1234", "test1234");
    }

    @Test
    @DisplayName("유저 생성 성공")
    void registerUser() throws Exception {
        // given

        // when
        User user = userService.registerUser(userDto);

        // then
        User userTest = userRepository.findById(user.getId()).orElseThrow(
                () -> new NullPointerException("User 가 정상적으로 생성되지 않았습니다.")
        );

        assertEquals("Id가 같은지 확인", user.getId(), userTest.getId());
    }

    @Test
    @DisplayName("유저 정보 업데이트 성공")
    void updateProfile() throws IOException {
        // given
        User user = userService.registerUser(userDto);
        user.setProfileImgUrl("C:\\Users\\wkdgy\\OneDrive\\바탕 화면\\Summer_beach.jpg");
        UserDetailsImpl nowUser = new UserDetailsImpl(user);

        MockMultipartFile multipartFileEdit = new MockMultipartFile("image",
                "testEdit.png",
                "image/png",
                new FileInputStream("C:\\Users\\wkdgy\\OneDrive\\바탕 화면\\로그인 수정.PNG"));

        // when
        User userTest = userService.updateProfile("test1234-edit", multipartFileEdit, nowUser);

        // then
        assertEquals("Id가 같은지 확인", user.getId(), userTest.getId());
        assertEquals("nickname 업데이트 확인", "test1234-edit", userTest.getNickname());
        System.out.println("profileImgUrl 의 값이 변경되었는지 확인." + userTest.getProfileImgUrl());
    }

    @Test
    @DisplayName("유저 ID 중복 체크 성공")
    void checkExist() throws Exception {
        // given
        User user = userService.registerUser(userDto);

        // when
        try {
            userService.checkExist(userDto);
        }

        // then
        catch (Exception e) {
            assertEquals("이미 저장된 이름일 경우 IllegalArgumentException 에러가 발생", 1, userRepository.findAll().size());
        }
    }

    @Test
    @DisplayName("유저 삭제 성공")
    void deleteUser() throws Exception {
        // given
        User user = userService.registerUser(userDto);
        UserDetailsImpl nowUser = new UserDetailsImpl(user);

        // when
        userService.deleteUser(nowUser);

        // then
        Optional<User> userTest = userRepository.findById(user.getId());

        if (userTest.isPresent())
            throw new IllegalArgumentException("User 가 정상적으로 삭제되지 않았습니다.");
        else
            assertEquals("User 가 정상적으로 삭제되었습니다.", Optional.empty(), userTest);

    }
}
