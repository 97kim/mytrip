package shop.kimkj.mytrip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.domain.UserReview;
import shop.kimkj.mytrip.dto.UserReviewDto;
import shop.kimkj.mytrip.repository.UserRepository;
import shop.kimkj.mytrip.repository.UserReviewRepository;

import javax.transaction.Transactional;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.Is.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class UserReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserReviewRepository userReviewRepository;

    private User user;

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
        user = new User("testId", "testPassword", "testNickname", "http://placeimg.com/640/480/nature");
        userRepository.save(user);
    }

    @AfterTransaction
    void deleteUser() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("리뷰 생성1")
    @WithUserDetails(value = "testId")
    @Order(1)
    public void postReview() throws Exception {
        UserReviewDto userReviewDto = new UserReviewDto("testTitle", "testPlace", "testReview");
        String jsonString = objectMapper.writeValueAsString(userReviewDto);

        MockPart jsonPart = new MockPart("review_data", jsonString.getBytes(StandardCharsets.UTF_8));
        jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile image = new MockMultipartFile("review_img", "image.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());

        mockMvc.perform(multipart("/review")
                        .file(image).part(jsonPart))

                .andExpect(status().isOk())
                .andExpect(jsonPath("title", is("testTitle")))
                .andExpect(jsonPath("place", is("testPlace")))
                .andExpect(jsonPath("review", is("testReview")))
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 생성2")
    @WithUserDetails(value = "testId")
    @Order(2)
    public void postReview2() throws Exception {
        UserReviewDto userReviewDto = new UserReviewDto("testTitle", "testPlace", "testReview");
        String jsonString = objectMapper.writeValueAsString(userReviewDto);

        MockMultipartFile jsonPart = new MockMultipartFile("review_data", "json", "application/json", jsonString.getBytes(StandardCharsets.UTF_8));
        MockMultipartFile image = new MockMultipartFile("review_img", "image.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());

        mockMvc.perform(multipart("/review")
                        .file(image)
                        .file(jsonPart))

                .andExpect(status().isOk())
                .andExpect(jsonPath("title", is("testTitle")))
                .andExpect(jsonPath("place", is("testPlace")))
                .andExpect(jsonPath("review", is("testReview")))
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 조회")
    @WithUserDetails(value = "testId")
    @Order(3)
    public void getReview() throws Exception {
        UserReviewDto userReviewDto = new UserReviewDto("testTitle", "testPlace", "testReview");
        UserReview userReview = new UserReview(userReviewDto, user);
        userReviewRepository.save(userReview);

        Long reviewId = userReview.getId();

        mockMvc.perform(get("/reviews/{reviewId}", reviewId))

                .andExpect(status().isOk())
                .andExpect(jsonPath("title", is("testTitle")))
                .andExpect(jsonPath("place", is("testPlace")))
                .andExpect(jsonPath("review", is("testReview")))
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 수정")
    @WithUserDetails(value = "testId")
    @Order(4)
    public void putReview() throws Exception {
        UserReviewDto userReviewDto = new UserReviewDto("testTitle", "testPlace", "testReview");
        UserReview userReview = new UserReview(userReviewDto, user);
        userReviewRepository.save(userReview);

        Long reviewId = userReview.getId();

        UserReviewDto changedReviewDto = new UserReviewDto("changedTitle", "changedPlace", "changedReview");
        String jsonString = objectMapper.writeValueAsString(changedReviewDto);

        MockMultipartFile jsonPart = new MockMultipartFile("review_data", "json", "application/json", jsonString.getBytes(StandardCharsets.UTF_8));
        MockMultipartFile image = new MockMultipartFile("review_img", "image.png", "image/png", "<<png data>>".getBytes());

        mockMvc.perform(multipart("/reviews/{reviewId}", reviewId)
                        .file(image)
                        .file(jsonPart)
                        .with(new RequestPostProcessor() {
                            @Override
                            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                                request.setMethod("PUT");
                                return request;
                            }
                        }))

                .andExpect(status().isOk())
                .andExpect(jsonPath("title", is("changedTitle")))
                .andExpect(jsonPath("place", is("changedPlace")))
                .andExpect(jsonPath("review", is("changedReview")))
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 삭제")
    @WithUserDetails(value = "testId")
    @Order(5)
    public void deleteReview() throws Exception {
        UserReviewDto userReviewDto = new UserReviewDto("testTitle", "testPlace", "testReview");
        UserReview userReview = new UserReview(userReviewDto, user);
        userReview.setReviewImgUrl("http://placeimg.com/640/480/nature");
        userReviewRepository.save(userReview);

        Long reviewId = userReview.getId();

        mockMvc.perform(delete("/reviews/{reviewId}", reviewId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 리스트 최근순 조회")
    @Order(6)
    public void getReviewsByDate() throws Exception {
        mockMvc.perform(get("/reviews")
                        .param("sort","date"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 리스트 좋아요순 조회")
    @Order(7)
    public void getReviewsByLike() throws Exception {
        mockMvc.perform(get("/reviews")
                        .param("sort","like"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("좋아요 저장")
    @Order(8)
    public void doLike() throws Exception {
        mockMvc.perform(get("/reviews")
                        .param("sort","like"))
                .andExpect(status().isOk())
                .andDo(print());
    }



}