package shop.kimkj.mytrip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.dto.UserDto;
import shop.kimkj.mytrip.repository.UserRepository;
import shop.kimkj.mytrip.service.UserService;

import javax.transaction.Transactional;


import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @BeforeTransaction
    void registerUser() {
        User user = new User("testId", "testPassword", "testNickname", "http://placeimg.com/640/480/nature");
        userRepository.save(user);
    }

    @AfterTransaction
    void deleteUser() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
    @Order(1)
    public void signIn() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setUsername("testId2");
        userDto.setPassword("testPassword2");

        String jsonString = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인")
    @Order(2)
    public void logIn() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setUsername("testId");
        userDto.setPassword("testPassword");

        String jsonString = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("아이디 유효성 검사")
    @Order(3)
    public void signupCheck() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("testId");

        String jsonString = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/signup/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("프로필 설정")
    @WithUserDetails(value = "testId")
    @Order(4)
    public void updateProfile() throws Exception {
                MockMultipartFile image = new MockMultipartFile("profileImgUrl", "image.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());
        mockMvc.perform(multipart("/user")
                        .file(image).part(new MockPart("nickname", "changedNickname".getBytes(StandardCharsets.UTF_8))))

                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 탈퇴")
    @WithUserDetails(value = "testId")
    @Order(5)
    public void withdrawal() throws Exception {
        mockMvc.perform(delete("/user"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
