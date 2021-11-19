function getId() {
    const URLSearch = new URLSearchParams(location.search);
    return URLSearch.get('id');
}

function getItem() {
    $.ajax({
        type: "POST",
        url: `/trips/place/render`,
        data: {trip_id_give: getId()},
        success: function (response) {
            $('#profile_img').attr('src', `https://dk9q1cr2zzfmc.cloudfront.net/profile/${response['trip']['profile_img']}`);
            $('#nickname').text(response['trip']['nickname']);
            $('#title').text(response['trip']['title']);
            $('#file').attr('src', `https://dk9q1cr2zzfmc.cloudfront.net/trips/${response['trip']['file']}`);
            $('#place').text(response['trip']['place']);
            $('#review').text(response['trip']['review']);
            $('#date').text(response['trip']['date']);
            $('#like').text(response['trip']['like']);
        }
    });
}

function updateTrip(trip_id) {
    $.ajax({
        type: "POST",
        url: '/trips/session',
        data: {trip_id_give: trip_id},
        success: function (response) {
            sessionStorage.setItem('title', response['title']);
            sessionStorage.setItem('place', response['place']);
            sessionStorage.setItem('review', response['review']);
            sessionStorage.setItem('file', response['file']);

            window.location.href = '../templates/tripsUpdate.html';
        }
    });
}

// 사용자가 올린 여행지 리뷰 삭제
function delTrip(trip_id) {
    $.ajax({
        type: "DELETE",
        url: `/trips/place/${trip_id}`,
        data: {},
        success: function (response) {
            alert(response['msg'])
            window.location.href = "../templates/tripsList.html";
        }
    });

}

// 카카오톡 공유하기
function kakaoShare() {
    $.ajax({
        type: "POST",
        url: `/trips/place/render`,
        data: {trip_id_give: getId()},
        success: function (response) {
            let share_title = response['trip']['title'];
            let share_place = response['trip']['place'];
            let share_img = `https://dk9q1cr2zzfmc.cloudfront.net/trips/${response['trip']['file']}`;
            let share_like = response['trip']['like'];
            let share_comment_count = response['comment_count'];

            Kakao.Link.sendDefault({
                objectType: 'feed',
                content: {
                    title: share_title,
                    description: share_place,
                    imageUrl: share_img,
                    link: {
                        mobileWebUrl: location.href,
                        webUrl: location.href
                    },
                },
                // 나중에 변수 추가할 것임!!
                social: {
                    likeCount: parseInt(share_like),
                    commentCount: share_comment_count,
                    sharedCount: 1
                },
                buttons: [
                    {
                        title: '구경 가기',
                        link: {
                            mobileWebUrl: location.href,
                            webUrl: location.href
                        }
                    }
                ],
            })
        }
    });
}

// 좋아요 기능
function toggle_like(trip_id) {
    let like = parseInt($('#like').text());

    if (!localStorage.getItem('token')) {
        alert('로그인이 필요한 서비스입니다.')
        window.location.href = "../templates/login.html"
    } else {
        if ($('#like').hasClass("fas")) {

            $.ajax({
                type: "POST",
                url: "/trips/place/like",
                data: {
                    trip_id_give: trip_id,
                    action_give: "uncheck"
                },
                success: function (response) {
                    if (response['result'] == 'success') {
                        $('#like').removeClass("fas").addClass("far")
                        $('#like').text(like - 1);
                    }
                }
            })
        } else {
            $.ajax({
                type: "POST",
                url: "/trips/place/like",
                data: {
                    trip_id_give: trip_id,
                    action_give: "check"
                },
                success: function (response) {
                    if (response['result'] == 'success') {
                        $('#like').removeClass("far").addClass("fas")
                        $('#like').text(like + 1);
                    }
                }
            });
        }
    }
}

function get_like() {
    $.ajax({
        type: "GET",
        url: `/trips/place/like/${getId()}`,
        data: {},
        success: function (response) {
            if (response['like_status'] == true) {
                $('#like').removeClass("far").addClass("fas");
            } else {
                $('#like').removeClass("fas").addClass("far")
            }
        }
    });
}

function comment() {
    let comment = $('#comment_content').val();
    let date = Date();
    if (!localStorage.getItem('token')) {
        alert('로그인이 필요한 서비스입니다.')
        window.location.href = "../templates/login.html"
    } else {
        if (comment) {
            $.ajax({
                type: "POST",
                url: `/trips/place/comment/${getId()}`,
                data: {comment_give: comment, date_give: date},
                success: function (response) {
                    if (response['result'] == 'success') {
                        $('#comment_content').val("");
                        showComments();
                    }
                }
            });
        } else {
            alert('댓글을 입력해주세요!')
        }
    }
}

function showComments() {
    $('#comment_list').empty();
    $.ajax({
        type: "GET",
        url: `/trips/place/comment/${getId()}`,
        data: {},
        success: function (response) {
            let all_comments = response['all_comments'];
            for (let i = 0; i < all_comments.length; i++) {
                let comment_id = all_comments[i]['_id'];
                let profile_img = all_comments[i]['profile_img'];
                let nickname = all_comments[i]['nickname'];
                let comment = all_comments[i]['comment'];
                let date = new Date(all_comments[i]['date']);
                let date_before = time2str(date);

                let html_temp = `<div class="mb-3">
                                    <div class="d-flex justify-content-between">
                                        <div class="d-flex align-items-center">
                                            <img src="https://dk9q1cr2zzfmc.cloudfront.net/profile/${profile_img}" width="35px" height="35px" style="object-fit: cover; border-radius: 50%;" />
                                            <span style="margin-left: 5px; font-size: 15px; font-weight: 700;">${nickname}</span>
                                            <span style="margin-left: 5px; font-size: 13px;">${date_before}</span>
                                        </div>
                                        <a id="${comment_id}" href="javascript:deleteComment('${comment_id}')" style="display: none;"><i class="fas fa-trash-alt" style="color: #6E85B2;"></i></a>
                                    </div>
                                    <div style="margin: 5px 0 0 5px; word-break:break-all; font-size: 14px; font-weight: 400;">${comment}</div>
                                 </div>`;
                $('#comment_list').append(html_temp);

                // 로그인한 유저와 댓글을 쓴 유저가 같으면 삭제 아이콘이 뜸
                if (response['now_user'] == all_comments[i]['username']) {
                    $(`#${comment_id}`).css('display', 'block');
                } else {
                    $(`#${comment_id}`).css('display', 'none');
                }
            }
        }
    });
}

function deleteComment(comment_id) {
    $.ajax({
        type: "DELETE",
        url: `/trips/place/comment/${getId()}`,
        data: {comment_id: comment_id},
        success: function (response) {
            if (response['result'] == 'success') {
                alert('댓글 삭제 완료!');
                showComments();
            }
        }
    });
}

function time2str(date) {
    let today = new Date();
    let time = (today - date) / 1000 / 60;  // 분

    if (time < 1) {
        return "방금";
    }

    if (time < 60) {
        return parseInt(time) + "분 전";
    }
    time = time / 60  // 시간
    if (time < 24) {
        return parseInt(time) + "시간 전";
    }
    time = time / 24
    if (time < 7) {
        return parseInt(time) + "일 전";
    }
    return `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일`;
}

function autoHeight() {
    $('textarea').keyup(function (e) {
        $(this).css('height', 'auto');
        $(this).height(this.scrollHeight);
    });
}

function ownCheck() {
    $.ajax({
        type: "POST",
        url: '/own',
        data: {trip_id: getId()},
        success: function (response) {
            if (response['owner']['username'] == response['now_user']) {
                $('#own-check').show();
            } else {
                $('#own-check').hide();
            }
        }
    });

}