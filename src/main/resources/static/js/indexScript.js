// slick 슬라이드
function slide() {
    $(function () {
        // Uncaught TypeError: Cannot read property 'add' of null” 오류 -> slick을 여러번 불러와서 발생
        // .not('.slick-initialized')로 하면 오류가 안 난다.

        // $('.slider-li').slick({
        $('.slider-li').not('.slick-initialized').slick({
            slide: 'li',		//슬라이드 되어야 할 태그 ex) div, li
            infinite: true, 	//무한 반복 옵션
            slidesToShow: 3,		// 한 화면에 보여질 컨텐츠 개수
            slidesToScroll: 1,		//스크롤 한번에 움직일 컨텐츠 개수
            speed: 100,	 // 다음 버튼 누르고 다음 화면 뜨는데까지 걸리는 시간(ms)
            dots: false, 		// 스크롤바 아래 점으로 페이지네이션 여부
            autoplay: true,			// 자동 스크롤 사용 여부
            autoplaySpeed: 5000, 		// 자동 스크롤 시 다음으로 넘어가는데 걸리는 시간 (ms)
            pauseOnHover: true,		// 슬라이드 이동 시 마우스 호버하면 슬라이더 멈추게 설정
            vertical: false,		// 세로 방향 슬라이드 옵션
            arrows: true, 		// 옆으로 이동하는 화살표 표시 여부
            prevArrow: $('#btn_prev'),		// 이전 화살표 모양 설정
            nextArrow: $('#btn_next'),		// 다음 화살표 모양 설정
            draggable: true, 	//드래그 가능 여부

            responsive: [ // 반응형 웹 구현 옵션
                {
                    breakpoint: 1500, //화면 사이즈 1500px 보다 작을 시
                    settings: {
                        //위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
                        slidesToShow: 2
                    }
                },
                {
                    breakpoint: 800, //화면 사이즈 800px 보다 작을 시
                    settings: {
                        //위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
                        slidesToShow: 1
                    }
                }
            ]
        });
    })
}

function slide2() {
    $(function () {
        // Uncaught TypeError: Cannot read property 'add' of null” 오류 -> slick을 여러번 불러와서 발생
        // .not('.slick-initialized')로 하면 오류가 안 난다.

        // $('.slider-li2').slick({
        $('.slider-li2').not('.slick-initialized').slick({
            slide: 'li',		//슬라이드 되어야 할 태그 ex) div, li
            infinite: true, 	//무한 반복 옵션
            slidesToShow: 3,		// 한 화면에 보여질 컨텐츠 개수
            slidesToScroll: 1,		//스크롤 한번에 움직일 컨텐츠 개수
            speed: 100,	 // 다음 버튼 누르고 다음 화면 뜨는데까지 걸리는 시간(ms)
            dots: false, 		// 스크롤바 아래 점으로 페이지네이션 여부
            autoplay: true,			// 자동 스크롤 사용 여부
            autoplaySpeed: 5000, 		// 자동 스크롤 시 다음으로 넘어가는데 걸리는 시간 (ms)
            pauseOnHover: true,		// 슬라이드 이동 시 마우스 호버하면 슬라이더 멈추게 설정
            vertical: false,		// 세로 방향 슬라이드 옵션
            arrows: true, 		// 옆으로 이동하는 화살표 표시 여부
            prevArrow: $('#btn_prev2'),		// 이전 화살표 모양 설정
            nextArrow: $('#btn_next2'),		// 다음 화살표 모양 설정
            draggable: true, 	//드래그 가능 여부

            responsive: [ // 반응형 웹 구현 옵션
                {
                    breakpoint: 1500, //화면 사이즈 1500px 보다 작을 시
                    settings: {
                        //위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
                        slidesToShow: 2
                    }
                },
                {
                    breakpoint: 800, //화면 사이즈 800px 보다 작을 시
                    settings: {
                        //위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
                        slidesToShow: 1
                    }
                }
            ]
        });
    })
}

function slide3() {
    $(function () {
        // Uncaught TypeError: Cannot read property 'add' of null” 오류 -> slick을 여러번 불러와서 발생
        // .not('.slick-initialized')로 하면 오류가 안 난다.

        $('.slider-li3').not('.slick-initialized').slick({
            slide: 'li',		//슬라이드 되어야 할 태그 ex) div, li
            infinite: true, 	//무한 반복 옵션
            slidesToShow: 3,		// 한 화면에 보여질 컨텐츠 개수
            slidesToScroll: 1,		//스크롤 한번에 움직일 컨텐츠 개수
            speed: 100,	 // 다음 버튼 누르고 다음 화면 뜨는데까지 걸리는 시간(ms)
            dots: false, 		// 스크롤바 아래 점으로 페이지네이션 여부
            autoplay: true,			// 자동 스크롤 사용 여부
            autoplaySpeed: 5000, 		// 자동 스크롤 시 다음으로 넘어가는데 걸리는 시간 (ms)
            pauseOnHover: true,		// 슬라이드 이동 시 마우스 호버하면 슬라이더 멈추게 설정
            vertical: false,		// 세로 방향 슬라이드 옵션
            arrows: true, 		// 옆으로 이동하는 화살표 표시 여부
            prevArrow: $('#btn_prev3'),		// 이전 화살표 모양 설정
            nextArrow: $('#btn_next3'),		// 다음 화살표 모양 설정
            draggable: true, 	//드래그 가능 여부

            responsive: [ // 반응형 웹 구현 옵션
                {
                    breakpoint: 1500, //화면 사이즈 1500px 보다 작을 시
                    settings: {
                        //위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
                        slidesToShow: 2
                    }
                },
                {
                    breakpoint: 800, //화면 사이즈 800px 보다 작을 시
                    settings: {
                        //위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
                        slidesToShow: 1
                    }
                }
            ]

        });
    })
}

// 현재 위치 불러와 근처 여행지 조회
function geoInfo() {
    function onGeoOK(position) { //위치 정보 공유 승인 시
        const lat = position.coords.latitude; //위도
        const lng = position.coords.longitude; //경도
        sessionStorage.setItem('type', 'trip');

        $.ajax({
                type: "POST",
                url: "/near",
                contentType: "application/json",
                data: JSON.stringify(
                    {place_lat: lat, place_lng: lng}),
                success: function (response) {
                    $('#near_card').empty();
                    response = JSON.parse(response)

                    for (let i = 0; i < response.length; i++) {
                        let title = response[i]['title'];
                        let address = response[i]['addr1'];
                        let file = response[i]['firstimage'];
                        if (!file) {
                            file = "https://dk9q1cr2zzfmc.cloudfront.net/img/noImage.png";
                        }
                        let distance = response[i]['dist'];
                        let content_id = response[i]['contentid'];

                        let temp_html = `<li style="margin: 0 10px; height: 300px;">
                                             <a href="javascript:moveNearDetail(${content_id})" class="card">
                                                <img src="${file}" class="card__image" alt="내 위치 근처 여행지 사진"/>
                                                <div class="card__overlay">
                                                    <div class="card__header">
                                                        <svg class="card__arc" xmlns="https://www.w3.org/TR/2018/CR-SVG2-20181004/">
                                                            <path/>
                                                        </svg>
                                                        <img class="card__thumb" src="${file}" alt="썸네일"/>
                                                        <div class="card__header-text">
                                                            <h3 class="card__title">${title}</h3>
                                                            <span class="card__status">${distance}m</span>
                                                        </div>
                                                    </div>
                                                    <p class="card__description">${address}</p>
                                                </div>
                                            </a>
                                        </li>`;
                        $('#near_card').append(temp_html);
                        slide();
                    }
                }
            }
        )
    }

// 1번째 파라미터: 위치 공유 승인 시, 2번째 파라미터: 위치 공유 거부 시 실행
    navigator.geolocation.getCurrentPosition(onGeoOK, onGeoError);
}

function moveNearDetail(content_id) {
    window.location.href = `../templates/nearDetail.html?id=${content_id}`;
}

function onGeoError() { //위치 정보 공유 거부 시
    alert('현재 위치를 찾을 수 없습니다.')
}


function showTripReviews() {
    $.ajax({
        type: "GET",
        url: "/userReviews",
        data: {},
        success: function (response) {
            let userReviews = response;

            for (let i = 0; i < userReviews.length; i++) {
                let tripId = userReviews[i]['id'];
                let tripTitle = userReviews[i]['title'];
                let tripPlace = userReviews[i]['place'];
                let tripFile = "tripFile";
                let tripDate = "tripDate";
                let tripLike = "tripLike";
                let tripProfileImg = "tripProfileImg";
                let tripNickname = "tripNickname";

                let temp_html = `<li style="margin: 0 10px; height: 300px;">
                                        <a onclick="moveTripDetail(${tripId})" class="card">
                                            <img src="https://dk9q1cr2zzfmc.cloudfront.net/trips/${tripFile}" class="card__image" alt="사용자가 올린 여행지 사진"/>
                                            <div class="card__overlay">
                                                <div class="card__header">
                                                    <svg class="card__arc" xmlns="https://www.w3.org/TR/2018/CR-SVG2-20181004/">
                                                        <path/>
                                                    </svg>
                                                    <div class="card__thumb2">
                                                        <img src="https://dk9q1cr2zzfmc.cloudfront.net/profile/${tripProfileImg}" alt="프로필 사진"/>
                                                    </div>
                                                    <div class="card__header-text">
                                                        <h3 class="card__title">${tripTitle}</h3>
                                                        <i class="far fa-thumbs-up">${tripLike}</i>
                                                        <span class="card__status">${tripDate}</span>
                                                    </div>
                                                </div>
                                                <p class="card__description">${tripPlace}</p>
                                                <p class="card__description">by <b>@${tripNickname}</b></p>
                                            </div>
                                        </a>
                                    </li>`
                $('#trip_card').append(temp_html);
                slide2();
            }
        }
    })
}

function moveTripDetail(trip_id) {
    window.location.href = `../templates/tripDetail.html?id=${trip_id}`;
}

function showPopularTrips() {
    $.ajax({
        type: 'POST',
        url: '/popular/trips',
        data: {},
        success: function (response) {
            $('#popular_card').empty();
            response = JSON.parse(response);
            let popular_list = response['popular_list'];
            let trip_theme = response['trip_theme'];

            // popularListScript 정보 전달용
            sessionStorage.setItem('cat1', response['cat1']);
            sessionStorage.setItem('cat2', response['cat2']);
            sessionStorage.setItem('cat3', response['cat3']);

            for (let i = 0; i < popular_list.length; i++) {
                let content_id = popular_list[i]['contentid'];
                let title = popular_list[i]['title'];
                let file = popular_list[i]['firstimage'];
                if (!file)
                    file = "https://dk9q1cr2zzfmc.cloudfront.net/img/noImage.png";
                let areacode = parseInt(popular_list[i]['areacode']);
                let address = checkAddress(areacode);

                let covid = checkCovid(address)
                let covid_city_name = covid_check_city(covid)
                let covid_count = JSON.parse(sessionStorage.getItem('covid_info'))[covid_city_name]['newCcase']

                let mapx = popular_list[i]['mapx'];
                let mapy = popular_list[i]['mapy'];
                if (!mapx || !mapy) {
                    mapx = 0;
                    mapy = 0;
                }

                let temp_html = `<li style="margin: 0 10px; height: 300px;">
                                 <a href="javascript:movePopularDetail(${content_id})" class="card">
                                    <img src="${file}" class="card__image" alt="내 위치 근처 여행지 사진"/>
                                    <div class="card__overlay">
                                        <div class="card__header">
                                            <svg class="card__arc" xmlns="https://www.w3.org/TR/2018/CR-SVG2-20181004/">
                                                <path/>
                                            </svg>
                                            <img class="card__thumb" src="${file}" alt="썸네일"/>
                                            <div class="card__header-text">
                                                <h3 class="card__title">${title}</h3>
                                                <span class="card__status">${address}</span>
                                            </div>
                                        </div>
                                        <p class="card__description">이 지역의 일일코로나 감염자 수 ${covid_count}명</p>
                                    </div>
                                </a>
                            </li>`;
                $('#popular_card').append(temp_html);
            }
            $('#popular_theme').prepend(trip_theme)
            slide3();
        }
    });
}

function movePopularDetail(content_id) {
    window.location.href = `../templates/popularDetail.html?id=${content_id}`;
}

function covid() {
    $.ajax({
        type: 'GET',
        url: 'https://api.corona-19.kr/korea/country/new/?serviceKey=eRyPhYXEzDktxKJ8QOUwcACLjHd5msf4M',
        data: {},
        success: function (response) {
            sessionStorage.setItem('covid_info', JSON.stringify(response))
        }
    });
}

function checkCovid(str) {
    let address = '';
    if (str.length > 3) {
        let a = str.charAt(0)
        let b = str.charAt(2)
        address = a + b
    } else
        address = str.substr(0, 2)
    return address
}

function covid_check_city(address) {
    let city = "";
    if (address === "서울") {
        city = "seoul"
    } else if (address === "부산") {
        city = "busan"
    } else if (address === "대구") {
        city = "daegu"
    } else if (address === "인천") {
        city = "incheon"
    } else if (address === "광주") {
        city = "gwangju"
    } else if (address === "대전") {
        city = "daejeon"
    } else if (address === "울산") {
        city = "ulsan"
    } else if (address === "경기") {
        city = "gyeonggi"
    } else if (address === "강원") {
        city = "gangwon"
    } else if (address === "충북") {
        city = "chungbuk"
    } else if (address === "충남") {
        city = "chungnam"
    } else if (address === "전북") {
        city = "jeonbuk"
    } else if (address === "전남") {
        city = "jeonnam"
    } else if (address === "경북") {
        city = "gyeongbuk"
    } else if (address === "경남") {
        city = "gyeongnam"
    } else if (address === "제주") {
        city = "jeju"
    }
    return city;
}

// 중복 코드 정리 예정
function checkAddress(code) {
    if (code === 1) {
        code = '서울'
    } else if (code === 2) {
        code = '인천'
    } else if (code === 3) {
        code = '대전'
    } else if (code === 4) {
        code = '대구'
    } else if (code === 5) {
        code = '광주'
    } else if (code === 6) {
        code = '부산'
    } else if (code === 7) {
        code = '울산'
    } else if (code === 8) {
        code = '세종특별자치시'
    } else if (code === 31) {
        code = '경기도'
    } else if (code === 32) {
        code = '강원도'
    } else if (code === 33) {
        code = '충청북도'
    } else if (code === 34) {
        code = '충청남도'
    } else if (code === 35) {
        code = '경상북도'
    } else if (code === 36) {
        code = '경상남도'
    } else if (code === 37) {
        code = '전라북도'
    } else if (code === 38) {
        code = '전라남도'
    } else if (code === 39) {
        code = '제주도'
    }
    return code
}

function writeTrip() {
    if (localStorage.getItem('token')) {
        window.location.href = "../templates/tripCreate.html";
    } else {
        alert('로그인이 필요한 서비스입니다.');
        window.location.href = "../templates/login.html";
    }
}