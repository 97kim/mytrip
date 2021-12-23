package shop.kimkj.mytrip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import shop.kimkj.mytrip.domain.Bookmark;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.dto.BookmarkDto;
import shop.kimkj.mytrip.repository.BookmarkRepository;
import shop.kimkj.mytrip.repository.UserRepository;
import shop.kimkj.mytrip.service.UserService;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc

class BookmarkControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    UserService userService;

    private Long contentId;

    private User user;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(MockMvcResultHandlers.print())
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
    @DisplayName("테마별 인기 여행지 즐겨찾기")
    @WithUserDetails(value = "testId")
    @Order(1)
    public void bookmarkTheme() throws Exception {
        contentId = 1997082L;

        BookmarkDto bookmarkDto = new BookmarkDto();
        bookmarkDto.setAddress("null");
        bookmarkDto.setTitle("testTitle");
        bookmarkDto.setImgUrl("testImg");

        String jsonString = objectMapper.writeValueAsString(bookmarkDto);
        mockMvc.perform(post("/themes/{contentId}/bookmark", contentId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("테마별 인기 여행지 즐겨찾기 해제")
    @WithUserDetails(value = "testId")
    @Order(2)
    public void unbookmarkTheme() throws Exception {
        contentId = 1997082L;

        mockMvc.perform(delete("/themes/{contentId}/bookmark", contentId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("테마별 인기 여행지 즐겨찾기 상태 False")
    @WithUserDetails(value = "testId")
    @Order(3)
    public void getPopularBookmarkStatusFalse() throws Exception{
        contentId = 1997082L;

        mockMvc.perform(get("/themes/{contentId}/bookmark", contentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("bookmarkStatus", Is.is(false)));
    }

    @Test
    @DisplayName("테마별 인기 여행지 즐겨찾기 상태 True")
    @WithUserDetails(value = "testId")
    @Order(4)
    public void getPopularBookmarkStatusTrue() throws Exception{
        contentId = 1997082L;
        BookmarkDto bookmarkDto = new BookmarkDto("testTitle", "testAddress", "http://placeimg.com/640/480/nature");

        Bookmark bookmark = new Bookmark(contentId, "popular", bookmarkDto, user);
        bookmarkRepository.save(bookmark);

        mockMvc.perform(get("/themes/{contentId}/bookmark", contentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("bookmarkStatus", Is.is(true)));

    }

    @Test
    @DisplayName("근처 여행지 즐겨찾기")
    @WithUserDetails(value = "testId")
    @Order(5)
    public void bookmarkNear() throws Exception{
        contentId = 756600L;

        BookmarkDto bookmarkDto = new BookmarkDto();
        bookmarkDto.setAddress("testAddress");
        bookmarkDto.setTitle("testTitle");
        bookmarkDto.setImgUrl("testImg");

        String jsonString = objectMapper.writeValueAsString(bookmarkDto);
        mockMvc.perform(post("/nearspots/{contentId}/bookmark", contentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());


    }
    @Test
    @DisplayName("근처 여행지 즐겨찾기 해제")
    @WithUserDetails(value = "testId")
    @Order(6)
    public void unBookmarkNear() throws Exception{
        contentId = 756600L;

        mockMvc.perform(delete("/nearspots/{contentId}/bookmark", contentId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("근처 여행지 즐겨찾기 상태 False")
    @WithUserDetails(value = "testId")
    @Order(7)
    public void getNearBookmarkStatusFalse() throws Exception{
        contentId = 756600L;

        mockMvc.perform(get("/nearspots/{contentId}/bookmark", contentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("bookmarkStatus", Is.is(false)));

    }

    @Test
    @DisplayName("근처 여행지 즐겨찾기 상태 True")
    @WithUserDetails(value = "testId")
    @Order(8)
    public void getNearBookmarkStatusTrue() throws Exception{
        contentId = 756600L;
        BookmarkDto bookmarkDto = new BookmarkDto("testTitle", "testAddress", "http://placeimg.com/640/480/nature");

        Bookmark bookmark = new Bookmark(contentId, "popular", bookmarkDto, user);
        bookmarkRepository.save(bookmark);

        mockMvc.perform(get("/nearspots/{contentId}/bookmark", contentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("bookmarkStatus", Is.is(true)));
    }

}