package shop.kimkj.mytrip.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shop.kimkj.mytrip.dto.LatLngDto;
import shop.kimkj.mytrip.dto.PopularDto;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class PopularService {

    @Value("${TOUR_KEY}")
    private String TOUR_KEY;

    @Value("${WEATHER_KEY}")
    private String WEATHER_KEY;

    public String getRandomType() {
        int info = (int) (Math.random() * 6) + 1;
        String cat1 = "C01";
        String cat2 = "";
        String cat3 = "";
        String tripTheme = "";

        switch (info) {
            case 1:
                cat2 = "C0112";
                cat3 = "C01120001";
                tripTheme = "가족";
                break;
            case 2:
                cat2 = "C0113";
                cat3 = "C01130001";
                tripTheme = "나홀로";
                break;
            case 3:
                cat2 = "C0114";
                cat3 = "C01140001";
                tripTheme = "힐링";
                break;
            case 4:
                cat2 = "C0115";
                cat3 = "C01150001";
                tripTheme = "걷기 좋은";
                break;
            case 5:
                cat2 = "C0116";
                cat3 = "C01160001";
                tripTheme = "캠핑";
                break;
            case 6:
                cat2 = "C0117";
                cat3 = "C01170001";
                tripTheme = "맛집";
                break;
        }

        StringBuffer result = new StringBuffer();
        JSONObject jsonObject = null;
        try {
            String apiUrl = String.format(
                    "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?" +
                            "serviceKey=%s" +
                            "&contentTypeId=25" +
                            "&listYN=Y" +
                            "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide" +
                            "&arrange=P" +
                            "&numOfRows=13" +
                            "&pageNo=1" +
                            "&cat1=%s" +
                            "&cat2=%s" +
                            "&cat3=%s"
                    , TOUR_KEY, cat1, cat2, cat3);

            URL url = new URL(apiUrl);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));

            String returnLine;

            while ((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }

            jsonObject = XML.toJSONObject(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONArray popularList = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("popular_list", popularList);
        jsonObject2.put("trip_theme", tripTheme);
        jsonObject2.put("cat1", cat1);
        jsonObject2.put("cat2", cat2);
        jsonObject2.put("cat3", cat3);

        return jsonObject2.toString();
    }

    public String getPopularPlaceList(PopularDto popularDto) {
        String quantity = popularDto.getQuantity();
        String cat1 = popularDto.getCat1();
        String cat2 = popularDto.getCat2();
        String cat3 = popularDto.getCat3();

        StringBuffer result = new StringBuffer();
        JSONObject jsonObject = null;
        try {
            String apiUrl = String.format(
                    "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?" +
                            "serviceKey=%s" +
                            "&contentTypeId=25" +
                            "&listYN=Y" +
                            "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide" +
                            "&arrange=P" +
                            "&numOfRows=%s" +
                            "&pageNo=1" +
                            "&cat1=%s" +
                            "&cat2=%s" +
                            "&cat3=%s"
                    , TOUR_KEY, quantity, cat1, cat2, cat3);

            URL url = new URL(apiUrl);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));

            String returnLine;

            while ((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }

            jsonObject = XML.toJSONObject(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONArray popularList = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("popular_list", popularList);
        jsonObject2.put("cat1", cat1);
        jsonObject2.put("cat2", cat2);
        jsonObject2.put("cat3", cat3);

        return jsonObject2.toString();
    }

    public String getPopularDetailIntro(Long contentId) {
        StringBuffer result = new StringBuffer();
        JSONObject jsonObject = null;
        try {
            String apiUrl = String.format(
                    "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?" +
                            "ServiceKey=%s&contentId=%s" +
                            "&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y" +
                            "&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y" +
                            "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide"
                    , TOUR_KEY, contentId);

            URL url = new URL(apiUrl);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));

            String returnLine;

            while ((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }

            jsonObject = XML.toJSONObject(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject popularDetail = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");

        return popularDetail.toString();
    }

    public String getWeatherPopular(LatLngDto latLngDto) {
        String placeLat = latLngDto.getPlaceLat();
        String placeLng = latLngDto.getPlaceLng();

        StringBuffer result = new StringBuffer();
        try {
            String apiUrl = String.format(
                    "https://api.openweathermap.org/data/2.5/weather?" +
                            "lat=%s&lon=%s" +
                            "&appid=%s&units=metric"
                    , placeLat, placeLng, WEATHER_KEY);

            URL url = new URL(apiUrl);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));

            String returnLine;

            while ((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}