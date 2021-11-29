// slick 슬라이드
function slide1() {
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

// slick 슬라이드
function slide2() {
    $(function () {
        // Uncaught TypeError: Cannot read property 'add' of null” 오류 -> slick을 여러번 불러와서 발생
        // .not('.slick-initialized')로 하면 오류가 안 난다.

        // $('.slider-li').slick({
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

function showPopularBookmarks() {
    $.ajax({
        type: "GET",
        url: "/popular/bookmark?type=popular",
        contentType: "application/json",
        data: {},
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let content_id = response[i]["contentId"]
                let title = response[i]["title"]
                let file = response[i]["imgUrl"]

                let temp_html = `<li style="margin: 0 10px 20px 10px; height: 300px;">
                                     <a href="javascript:movePopularDetail(${content_id})" class="card">
                                        <img src="${file}" class="card__image" alt="즐겨찾기한 여행지 사진"/>
                                        <div class="card__overlay">
                                            <div class="card__header">
                                                <svg class="card__arc" xmlns="https://www.w3.org/TR/2018/CR-SVG2-20181004/">
                                                    <path/>
                                                </svg>
                                                <img class="card__thumb" src="${file}" alt="썸네일"/>
                                                <div class="card__header-text">
                                                    <h3 class="card__title">${title}</h3>
                                                </div>
                                            </div>
                                            <p class="card__description" ></p>
                                        </div>
                                    </a>
                                </li>`
                $('#popular_card').append(temp_html);
                slide1();
            }
        }
    });
}

function movePopularDetail(content_id) {
    window.location.href = `../templates/popularDetail.html?id=${content_id}`;
}

function showNearBookmarks() {
    $.ajax({
        type: "GET",
        url: "/near/bookmark?type=near",
        contentType: "application/json",
        data: {},
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let content_id = response[i]["contentId"]
                let title = response[i]["title"]
                let file = response[i]["imgUrl"]
                let address = response[i]["address"]

                let temp_html = `<li style="margin: 0 10px 20px 10px; height: 300px;">
                                     <a href="javascript:moveNearDetail(${content_id})" class="card">
                                        <img src="${file}" class="card__image" alt="즐겨찾기한 여행지 사진"/>
                                        <div class="card__overlay">
                                            <div class="card__header">
                                                <svg class="card__arc" xmlns="https://www.w3.org/TR/2018/CR-SVG2-20181004/">
                                                    <path/>
                                                </svg>
                                                <img class="card__thumb" src="${file}" alt="썸네일"/>
                                                <div class="card__header-text">
                                                    <h3 class="card__title">${title}</h3>
                                                </div>
                                            </div>
                                            <p class="card__description">${address}</p>
                                        </div>
                                    </a>
                                </li>`
                $('#near_card').append(temp_html);
                slide2();
            }
        }
    });
}

function moveNearDetail(content_id) {
    window.location.href = `../templates/nearDetail.html?id=${content_id}`;
}