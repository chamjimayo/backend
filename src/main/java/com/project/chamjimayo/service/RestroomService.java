package com.project.chamjimayo.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.chamjimayo.domain.entity.Restroom;
import com.project.chamjimayo.repository.RestroomRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RestroomService {

    private final RestroomRepository restroomRepository;
    private final Environment env;

    public ArrayList<Map> readJson() throws Exception {
        /* local에 있는 파일 사용
        Reader reader = new FileReader("/Users/kick_sim/Downloads/seoulRestroom.json");
        JSONParser parser = new JSONParser(reader);
        ArrayList<Map> restroomList = (ArrayList<Map>) parser.parse();
        return restroomList;
         */
        /* 구글 드라이브에 공유된 파일 사용*/
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Resource> response = restTemplate.exchange(
            "https://drive.google.com/uc?id=1t0hgQV9Ud4MDYYFj2z11EdwxyPd1265W&export=download",
            HttpMethod.GET,
            null,
            Resource.class
        );
        Resource resource = response.getBody(); // Resource객체 추출, Resource는 추상화된 리소스를 다루는 인터페이스임


        /*(Resource) 객체에서 InputStream을 얻어옴. InputStream은 파일의 내용을 바이트 스트림 형태로 읽어오는데 사용*/
        try (InputStream inputStream = resource.getInputStream()) {
            /*JSON 데이터를 파싱하기 위한 ObjectMapper 객체를 생성*/
            ObjectMapper objectMapper = new ObjectMapper();
            /* ObjectMapper를 사용해 ArrayList<Map> 형태의 자바 객체로 변환 */
            ArrayList<Map> dataObject = objectMapper.readValue(inputStream,
                new TypeReference<ArrayList<Map>>() {
                });
            return dataObject;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double[] getLongNLat(String address) {
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="
            + address; //네이버 cloud platform GeoCoding 사용
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        String client_Id = env.getProperty("naver.client-id");
        String client_Secret = env.getProperty("naver.client-secret");
        httpHeaders.add("X-NCP-APIGW-API-KEY-ID", client_Id);
        httpHeaders.add("X-NCP-APIGW-API-KEY", client_Secret);
        ResponseEntity<Map> response = restTemplate.exchange(
            apiUrl,
            HttpMethod.GET,
            new HttpEntity(httpHeaders),
            Map.class
        );
        ArrayList<Map> responseArrayList = (ArrayList<Map>) response.getBody().get("addresses");
        Map responseMap = responseArrayList.get(0);
        double longAndLat[] = {Double.parseDouble((String) responseMap.get("x")),
            Double.parseDouble((String) responseMap.get("y"))};
        return longAndLat;
    }

    public void importRestroom() throws Exception {
        ArrayList<Map> restroomList = readJson();
        for (Map restroom_info : restroomList) {
            //Map restroom_info = restroomList.get(1000); // TEST
            double[] longNLat = getLongNLat(
                (String) restroom_info.get("소재지주소")); // 소재지 주소를 통해 위도 경도 검색
            Restroom restroom = Restroom.builder()
                .restroomName((String) restroom_info.get("화장실명"))
                .locationLatitude(longNLat[1])
                .locationLongitude(longNLat[0])
                //unisex() 차후개발
                .roadAddress((String) restroom_info.get("소재지주소"))
                .operatingHour((String) restroom_info.get("개방시간"))
                .restroomPhoto("이미지 URL") // 차후개발
                .equipmentExistenceProbability(0)//차후개발
                .publicOrPaid("public")
                .accessibleToiletExistence(true) // 이용 가능 상태 default로 true
                .maleToiletCount(Integer.parseInt((String) restroom_info.get("남성용-대변기수")))
                .femaleToiletCount(Integer.parseInt((String) restroom_info.get("여성용-대변기수")))
                .availableMaleToiletCount(Integer.parseInt(
                    (String) restroom_info.get("남성용-대변기수")))// default를 전체 대변기 수로 설정)
                .availableFemaleToiletCount(Integer.parseInt(
                    (String) restroom_info.get("여성용-대변기수"))) // default를 전체 대변기 수로 설정
                .build();
            //restroomRepository.save(restroom); // 데이터베이스에 화장실 정보 저장
            System.out.println(restroom); //테스트
        }
    }
}