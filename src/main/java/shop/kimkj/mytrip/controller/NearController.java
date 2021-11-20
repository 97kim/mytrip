package shop.kimkj.mytrip.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@PropertySource("classpath:env.properties") // config.properties에 저장된 키 값 가져올 수 있게 경로 설정
public class NearController {

    @Value("${TOUR_KEY}")
    private String TOUR_KEY;

    @PostMapping("/near")
    public String getNearPlace(@RequestParam String lat_give, @RequestParam String lng_give) throws IOException {
        StringBuffer result = new StringBuffer();
        String jsonPrintString = null;
        try {
            String apiUrl = String.format(
                    "http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList?" +
                    "serviceKey=%s" +
                    "&contentTypeId=12" +
                    "&mapX=%s&mapY=%s" +
                    "&radius=4000&listYN=Y&MobileOS=ETC" +
                    "&MobileApp=TourAPI3.0_Guide" +
                    "&arrange=E" +
                    "&numOfRows=10" +
                    "&pageNo=1"
                    , TOUR_KEY, lng_give, lat_give);

            URL url = new URL(apiUrl);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));

            String returnLine;

            while((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }

            JSONObject jsonObject = XML.toJSONObject(result.toString());
            jsonPrintString = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(jsonPrintString);
        return jsonPrintString;
    }


    @PostMapping("/near/place/intro")
    public String getNearDetailIntro(@RequestParam(name = "content_id_give") String contentId) throws IOException {
        StringBuffer result = new StringBuffer();
        JSONObject jsonObject = null;
        try {
            String apiUrl = String.format(
                    "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?" +
                    "serviceKey=%s" +
                    "&contentId=%s" +
                    "&MobileOS=ETC" +
                    "&MobileApp=TourAPI3.0_Guide" +
                    "&defaultYN=Y&firstImageYN=Y" +
                    "&areacodeYN=Y&catcodeYN=Y" +
                    "&addrinfoYN=Y&mapinfoYN=Y" +
                    "&overviewYN=Y&transGuideYN=Y"
                    , TOUR_KEY, contentId);

            URL url = new URL(apiUrl);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));

            String returnLine;

            while((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }

            jsonObject = XML.toJSONObject(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject nearDetail = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");

        return nearDetail.toString();
    }


}