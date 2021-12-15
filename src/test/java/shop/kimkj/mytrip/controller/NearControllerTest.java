package shop.kimkj.mytrip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import shop.kimkj.mytrip.dto.NearDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // @Order로 순서 정하는 방법
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 라이프 사이클을 설정(PER_CLASS: Test 클래스 당 인스턴스 생성)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class NearControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long contentId;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    @Order(1)
    public void 근처_여행지_조회() throws Exception {

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("lat", "37.40210149436659"); // 위도
        info.add("lng", "127.10861806931135"); // 경도

        mockMvc.perform(get("/nearspots")
                        .params(info))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(2)
    public void 근처_여행지_상세_조회() throws Exception {
        contentId = 2500296L;
        mockMvc.perform(get("/nearspots/{contentId}", contentId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(3)
    public void 근처_여행지_trip_필터() throws Exception {
        NearDto nearDto = new NearDto();
        nearDto.setLat("37.40210149436659");
        nearDto.setLng("127.10861806931135");
        nearDto.setQuantity("20");
        nearDto.setType("trip");
        // json형식의 String으로 만들기 위해 objectMapper 사용
        String jsonString = objectMapper.writeValueAsString(nearDto);
        mockMvc.perform(post("/nearspots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(4)
    public void 근처_여행지_food_필터() throws Exception {
        NearDto nearDto = new NearDto();
        nearDto.setLat("37.40210149436659");
        nearDto.setLng("127.10861806931135");
        nearDto.setQuantity("20");
        nearDto.setType("food");
        // json형식의 String으로 만들기 위해 objectMapper 사용
        String jsonString = objectMapper.writeValueAsString(nearDto);
        mockMvc.perform(post("/nearspots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(5)
    public void 근처_여행지_accommodation_필터() throws Exception {
        NearDto nearDto = new NearDto();
        nearDto.setLat("37.40210149436659");
        nearDto.setLng("127.10861806931135");
        nearDto.setQuantity("20");
        nearDto.setType("accommodation");
        // json형식의 String으로 만들기 위해 objectMapper 사용
        String jsonString = objectMapper.writeValueAsString(nearDto);
        mockMvc.perform(post("/nearspots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(6)
    public void 근처_여행지_festival_필터() throws Exception {
        NearDto nearDto = new NearDto();
        nearDto.setLat("37.40210149436659");
        nearDto.setLng("127.10861806931135");
        nearDto.setQuantity("20");
        nearDto.setType("festival");
        // json형식의 String으로 만들기 위해 objectMapper 사용
        String jsonString = objectMapper.writeValueAsString(nearDto);
        mockMvc.perform(post("/nearspots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

}