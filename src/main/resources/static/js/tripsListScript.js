function showUserReview(type) {
    $('#trip_card').empty();
    $.ajax({
        type: "GET",
        url: `/userReviews`,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let userReviewIdx = response[i]['id'];
                let userReviewTitle = response[i]['title'];
                let userReviewPlace = response[i]['place'];
                let userReviewFile = response[i]['file'];
                let userReviewDate = response[i]['date'];
                let userReviewLikes = response[i]['like'];
                let userReviewProfile_img = response[i]['profile_img'];
                let userReviewNickname = response[i]['nickname'];

                let temp_html = `<li style="margin: 0 10px; height: 300px;">
                                        <a onclick="moveTripDetail(${userReviewIdx})" class="card">
                                            <img src="https://dk9q1cr2zzfmc.cloudfront.net/trips/${userReviewFile}" class="card__image" alt="사용자가 올린 여행지 사진"/>
                                            <div class="card__overlay">
                                                <div class="card__header">
                                                    <svg class="card__arc" xmlns="https://www.w3.org/TR/2018/CR-SVG2-20181004/">
                                                        <path/>
                                                    </svg>
                                                    <div class="card__thumb2">
                                                        <img src="https://dk9q1cr2zzfmc.cloudfront.net/profile/${userReviewProfile_img}" alt="프로필 사진"/>
                                                    </div>
                                                    <div class="card__header-text">
                                                        <h3 class="card__title">${userReviewTitle}</h3>
                                                        <i class="far fa-thumbs-up">${userReviewLikes}</i>
                                                        <span class="card__status">${userReviewDate}</span>
                                                    </div>
                                                </div>
                                                <p class="card__description">${userReviewPlace}</p>
                                                <p class="card__description">by <b>@${userReviewNickname}</b></p>
                                            </div>
                                        </a>
                                    </li>`
                $('#trip_card').append(temp_html);
            }
        }
    })
}

function moveTripDetail(trip_id) {
    window.location.href = `../templates/tripDetail.html?id=${trip_id}`;
}

function writeTrip() {
    if (localStorage.getItem('token')) {
        window.location.href = "../templates/tripCreate.html";
    } else {
        alert('로그인이 필요한 서비스입니다.');
        window.location.href = "../templates/login.html";
    }
}