function showListSort(type) {
    $('#trip_card').empty();
    $.ajax({
        type: "GET",
        url: `/trips?sort=${type}`,
        data: {},
        success: function (response) {
            let trip_list = response['all_trips'];

            for (let i = 0; i < trip_list.length; i++) {
                let trip_id = trip_list[i]['_id'];
                let trip_title = trip_list[i]['title'];
                let trip_place = trip_list[i]['place'];
                let trip_file = trip_list[i]['file'];
                let trip_date = trip_list[i]['date'];
                let trip_like = trip_list[i]['like'];
                let trip_profile_img = trip_list[i]['profile_img'];
                let trip_nickname = trip_list[i]['nickname'];

                let temp_html = `<li style="margin: 0 10px; height: 300px;">
                                        <a href="javascript:moveTripDetail('${trip_id}')" class="card">
                                            <img src="https://dk9q1cr2zzfmc.cloudfront.net/trips/${trip_file}" class="card__image" alt="사용자가 올린 여행지 사진"/>
                                            <div class="card__overlay">
                                                <div class="card__header">
                                                    <svg class="card__arc" xmlns="https://www.w3.org/TR/2018/CR-SVG2-20181004/">
                                                        <path/>
                                                    </svg>
                                                    <div class="card__thumb2">
                                                        <img src="https://dk9q1cr2zzfmc.cloudfront.net/profile/${trip_profile_img}" alt="프로필 사진"/>
                                                    </div>
                                                    <div class="card__header-text">
                                                        <h3 class="card__title">${trip_title}</h3>
                                                        <i class="far fa-thumbs-up">${trip_like}</i>
                                                        <span class="card__status">${trip_date}</span>
                                                    </div>
                                                </div>
                                                <p class="card__description">${trip_place}</p>
                                                <p class="card__description">by <b>@${trip_nickname}</b></p>
                                            </div>
                                        </a>
                                    </li>`
                $('#trip_card').append(temp_html);
            }
        }
    })
}

function moveTripDetail(trip_id) {
    window.location.href = `../templates/tripsDetail.html?id=${trip_id}`;
}

function writeTrip() {
    alert('로그인이 필요한 서비스입니다.');
    if (localStorage.getItem('token')) {
        window.location.href = "../templates/write.html";
    } else {
        window.location.href = "../templates/login.html";
    }
}