## 스파르타 내일배움캠프 3차 프로젝트

### 🔗 라이브
https://www.kimkj.shop/templates/index.html

<br>

### 🔖 Starting Assignment
https://velog.io/@rudwnd33/3%EC%B0%A8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-S.A

<br>

### 🏠 소개
지금 떠날 수 있는 여행지는 어디??

여행 가고 싶은 지역의 생생한 여행 정보, 여행 후기(날씨 등)를 확인 할 수 있는 여행어때와 함께 떠나보세요!

<br>

### ⏲️ 개발기간
2021년 11월 19일 ~ 12월 9일

<br>

### 🧙 멤버구성
김경중<br>
서태욱<br>
장효진

<br>

### 📌 기술 선택 이유!
<p>
<img src="https://img.shields.io/badge/Flask-000000?style=plastic&logo=Flask&logoColor=white"/>
<img src="https://img.shields.io/badge/MongoDB-47A248?style=plastic&logo=MongoDB&logoColor=white"/>
<img src="https://img.shields.io/badge/Bootstrap-7952B3?style=plastic&logo=Bootstrap&logoColor=white"/>
<img src="https://img.shields.io/badge/CodePen-000000?style=plastic&logo=CodePen&logoColor=white"/>
<img src="https://img.shields.io/badge/Bulma-00D1B2?style=plastic&logo=CodePen&logoColor=white"/>
<img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=plastic&logo=Amazon%20AWS&logoColor=white"/>
</p>

- 강의를 보면서 배웠던 기술들이고, 아직 무거운 웹앱을 만드는 것이 아니기 때문에 Flask와 MongoDB가 적합하다고 생각했습니다.
- css를 하나하나 조정하며 예쁘게 꾸미고 싶지만, 모두 백엔드 개발 희망자이기도 해서 UI는 부트스트랩, CodePen, Bulma를 참고했습니다.
- AWS EB와 GitHub Action을 통해 자동배포 환경을 구축했습니다.
- 이미지를 업로드할 시 AWS S3에 업로드가 되며 해당 이미지를 AWS Cloudfront를 통해 불러오도록 했습니다.

<br>

#### Open API 사용
- <a href="https://www.data.go.kr/iim/api/selectAPIAcountView.do">한국관광공사 API</a>
- <a href="https://openweathermap.org">날씨 API</a>
- <a href="https://www.ncloud.com/product/applicationService/maps">네이버 지도 API</a>
- <a href="https://developers.kakao.com/docs/latest/ko/message/js-link#default-template-msg">카카오톡 공유하기 API</a>

<br>

### 📌 주요 기능
반응형 설계
- 모바일 기기를 많이 사용하는 만큼 사용자가 보기 편하게 설계했습니다.

위치 API 기반 내 주변 여행지 추천
- 내 현재 위치를 기반으로 주변의 여행지를 추천합니다.
- 해당 여행지의 위치를 지도로 볼 수 있습니다.

날씨 API를 이용한 여행지 날씨 정보 제공
- 여행을 떠나려는 곳의 날씨 정보를 제공합니다.

내가 가본 여행지 리뷰 글 작성 / 수정 / 삭제
- 가본 여행지에 대한 리뷰 글을 작성, 조회, 수정, 삭제할 수 있습니다.

좋아요 / 즐겨찾기
- 좋아요 기능으로 리뷰 글을 추천할 수 있고, 내 주변 여행지를 즐겨찾기로 모아볼 수 있습니다.

<br>

### 📌 문제를 이렇게 해결했어요!
<a href="https://github.com/97kim/myTrip/wiki/4.-%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85">트러블슈팅</a>

<br>

### 📌 WIKI
<a href="https://github.com/97kim/myTrip/wiki">WIKI로 이동</a>

<br>

### 📌 기타
한국관광공사 API: 일일 트래픽 1000까지
<br>
날씨 API: 분당 트래픽 60까지
