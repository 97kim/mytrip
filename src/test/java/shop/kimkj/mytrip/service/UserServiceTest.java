package shop.kimkj.mytrip.service;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.dto.UserDto;
import shop.kimkj.mytrip.repository.UserRepository;

import static org.springframework.test.util.AssertionErrors.assertEquals;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("유저 생성 성공")
    void UserTest() {
        // given
        UserDto userDto = new UserDto("signup", "test1234", "test1234");
        User user = userService.registerUser(userDto);
        // when
        User test = userRepository.getById(user.getId());
        // then
        assertEquals("Id가 같은지 확인", user.getId(), test.getId());
    }
}
