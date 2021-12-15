package shop.kimkj.mytrip.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.dto.UserDto;
import shop.kimkj.mytrip.repository.UserRepository;

import static org.springframework.test.util.AssertionErrors.assertEquals;


@ExtendWith(MockitoExtension.class)
class UserTest {
    @Mock
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("유저 생성하기 - registerUser()")
    void postReviewService() {
        // given
        UserDto userDto = new UserDto("signup", "test1234", "test1234");

        String profileImgUrl = "test";
        String nickname = userDto.getUsername();

        User user = new User(userDto.getUsername(), userDto.getPassword(), nickname, profileImgUrl);
        userRepository.save(user);

        // when
        User test = userRepository.getById(user.getId());

        // then
        assertEquals("회원 생성하는 코드", user.getId(), test);
    }

}
