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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import shop.kimkj.mytrip.dto.LatLngDto;
import shop.kimkj.mytrip.dto.PopularDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class PopularControllerTest {
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
    public void 테마별_여행지_리스트_조회() throws Exception {
        PopularDto popularDto = new PopularDto();
        popularDto.setQuantity("20");
        popularDto.setCat1("C01");
        popularDto.setCat2("C0112");
        popularDto.setCat3("C01120001");

        String jsonString = objectMapper.writeValueAsString(popularDto);
        mockMvc.perform(post("/themes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(2)
    public void 테마별_여행지_상세_조회() throws Exception {
        contentId = 1920563L;
        mockMvc.perform(get("/themes/{contentId}", contentId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(3)
    public void 테마별_여행지_가족_필터() throws Exception{
        PopularDto popularDto = new PopularDto();
        popularDto.setQuantity("20");
        popularDto.setCat1("C01");
        popularDto.setCat2("C0112");
        popularDto.setCat3("C01120001");

        String jsonString = objectMapper.writeValueAsString(popularDto);
        mockMvc.perform(post("/themes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(4)
    public void 테마별_여행지_나홀로_필터() throws Exception{
        PopularDto popularDto = new PopularDto();
        popularDto.setQuantity("20");
        popularDto.setCat1("C01");
        popularDto.setCat2("C0113");
        popularDto.setCat3("C01130001");

        String jsonString = objectMapper.writeValueAsString(popularDto);
        mockMvc.perform(post("/themes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(5)
    public void 테마별_여행지_힐링_필터() throws Exception{
        PopularDto popularDto = new PopularDto();
        popularDto.setQuantity("20");
        popularDto.setCat1("C01");
        popularDto.setCat2("C0114");
        popularDto.setCat3("C01140001");

        String jsonString = objectMapper.writeValueAsString(popularDto);
        mockMvc.perform(post("/themes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(6)
    public void 테마별_여행지_걷기좋은_필터() throws Exception{
        PopularDto popularDto = new PopularDto();
        popularDto.setQuantity("20");
        popularDto.setCat1("C01");
        popularDto.setCat2("C0115");
        popularDto.setCat3("C01150001");

        String jsonString = objectMapper.writeValueAsString(popularDto);
        mockMvc.perform(post("/themes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(7)
    public void 테마별_여행지_캠핑_필터() throws Exception{
        PopularDto popularDto = new PopularDto();
        popularDto.setQuantity("20");
        popularDto.setCat1("C01");
        popularDto.setCat2("C0116");
        popularDto.setCat3("C01160001");

        String jsonString = objectMapper.writeValueAsString(popularDto);
        mockMvc.perform(post("/themes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(8)
    public void 테마별_여행지_맛집_필터() throws Exception{
        PopularDto popularDto = new PopularDto();
        popularDto.setQuantity("20");
        popularDto.setCat1("C01");
        popularDto.setCat2("C0117");
        popularDto.setCat3("C01170001");

        String jsonString = objectMapper.writeValueAsString(popularDto);
        mockMvc.perform(post("/themes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(8)
    public void 날씨() throws Exception{
        LatLngDto latLngDto = new LatLngDto();
        latLngDto.setPlaceLat("37.47472177841315");
        latLngDto.setPlaceLng("126.94872261770247");

        String jsonString = objectMapper.writeValueAsString(latLngDto);
        mockMvc.perform(post("/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }
}