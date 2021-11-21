function checkTheme(type, quantity) {
    type = parseInt(type)
    let cat1 = "C01";
    let cat2 = "";
    let cat3 = "";
    let content_type_id = "25";

    if (type === 1) {
        cat2 = 'C0112'
        cat3 = 'C01120001'
    } else if (type === 2) {
        cat2 = 'C0113'
        cat3 = 'C01130001'
    } else if (type === 3) {
        cat2 = 'C0114'
        cat3 = 'C01140001'
    } else if (type === 4) {
        cat2 = "C0115";
        cat3 = "C01150001";
    } else if (type === 5) {
        cat2 = "C0116";
        cat3 = "C01160001";
    } else if (type === 6) {
        cat2 = "C0117";
        cat3 = "C01170001";
    }
    // 정보 덮어쓰기
    sessionStorage.setItem('cat1', cat1);
    sessionStorage.setItem('cat2', cat2);
    sessionStorage.setItem('cat3', cat3);
    popularList(quantity)
}

// 추천여행지 출력, 추천여행지 선택한 결과 출력
function popularList(quantity) {
    $('#popular_card').empty();
    // index.html 에서 가져온 정보들, main 의 정보를 그대로 list 창에서 보여주기 위함
    let cat1 = sessionStorage.getItem('cat1')
    let cat2 = sessionStorage.getItem('cat2')
    let cat3 = sessionStorage.getItem('cat3')
    $('.before-render').show();


    $.ajax({
            type: "POST",
            url: "/popular/list",
            contentType: "application/json",
            data: JSON.stringify({
                quantity: quantity,
                cat1: cat1,
                cat2: cat2,
                cat3: cat3
            }),
            success: function (response) {
                $('.before-render').hide();
                $('#popular_card').empty();
                response = JSON.parse(response);
                let popular_list = response['popular_list'];

                let cat1 = response['cat1'];
                let cat2 = response['cat2'];
                let cat3 = response['cat3'];

                sessionStorage.setItem('cat1', cat1);
                sessionStorage.setItem('cat2', cat2);
                sessionStorage.setItem('cat3', cat3);

                for (let i = 0; i < popular_list.length; i++) {
                    let content_id = popular_list[i]['contentid'];
                    let title = popular_list[i]['title'];
                    let file = popular_list[i]['firstimage'];
                    if (!file)
                        file = "https://dk9q1cr2zzfmc.cloudfront.net/img/noImage.png";
                    let areacode = parseInt(popular_list[i]['areacode']);
                    let address = check_address(areacode);
                    let mapx = popular_list[i]['mapx'];
                    let mapy = popular_list[i]['mapy'];
                    if (!mapx || !mapy) {
                        mapx = 0;
                        mapy = 0;
                    }

                    let covid = checkCovid(address)
                    let covid_city_name = covid_check_city(covid)
                    let covid_count = JSON.parse(sessionStorage.getItem('covid_info'))[covid_city_name]['newCcase']

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
                                        <p class="card__description" >이 지역의 일일코로나 감염자 수 ${covid_count}명</p>
                                    </div>
                                </a>
                            </li>`;
                    $('#popular_card').append(temp_html);
                }
            }
        }
    )
}

function movePopularDetail(content_id) {
    window.location.href = `../templates/popularDetail.html?id=${content_id}`;
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

function check_address(code) {
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