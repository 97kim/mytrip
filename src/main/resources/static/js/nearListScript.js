function checkType(code, quantity) {
    code = parseInt(code);
    let type = "";

    if (code === 12) {
        type = "trip";
    } else if (code === 39) {
        type = "food";
    } else if (code === 32) {
        type = "accommodation";
    } else {
        type = "festival";
    }

    // 정보 덮어쓰기
    sessionStorage.setItem('type', type);
    geoInfoList(quantity)
}

// 현재 위치 불러와 근처 여행지, 음식점, 숙박, 축제공연행사 조회 (5km, 항목당 최대 40개)
function geoInfoList(quantity) {
    $('#near_card').empty();
    $('.before-render').show();
    let type = sessionStorage.getItem('type')

    function onGeoOK(position) { //위치 정보 공유 승인 시
        const lat = position.coords.latitude; //위도
        const lng = position.coords.longitude; //경도

        $.ajax({
                type: "POST",
                url: "/near/list",
                contentType: "application/json",
                data: JSON.stringify({
                    quantity_give: quantity,
                    lat_give: lat,
                    lng_give: lng,
                    type_give: type
                }),
                success: function (response) {
                    $('.before-render').hide();
                    $('#near_card').empty();
                    let near_list = JSON.parse(response)['near_list'];

                    for (let i = 0; i < near_list.length; i++) {
                        let title = near_list[i]['title'];
                        let address = near_list[i]['addr1'];
                        let file = near_list[i]['firstimage'];
                        if (!file) {
                            file = "https://dk9q1cr2zzfmc.cloudfront.net/img/noImage.png";
                        }
                        let distance = near_list[i]['dist'];
                        let content_id = near_list[i]['contentid'];

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
                    }
                }
            }
        )
    }

    function onGeoError() { //위치 정보 공유 거부 시
        alert('현재 위치를 찾을 수 없습니다.')
    }

    // 1번째 파라미터: 위치 공유 승인 시, 2번째 파라미터: 위치 공유 거부 시 실행
    navigator.geolocation.getCurrentPosition(onGeoOK, onGeoError);
}

function moveNearDetail(content_id) {
    window.location.href = `../templates/nearDetail.html?id=${content_id}`;
}