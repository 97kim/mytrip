function getId() {
    const URLSearch = new URLSearchParams(location.search);
    return URLSearch.get('id');
}

function getUserReview(id) {
    $.ajax({
        type: "GET",
        url: `/userReview/${id}`,
        success: function (response) {
            $('#title').text(response['title']);
            $('#place').text(response['place']);
            $('#review').text(response['review']);
            $('#nickname').text(response['user']['nickname']);
            $('#profile_img').attr('src', response['user']['profileImgUrl']);
            $('#file').attr('src', response['reviewImgUrl']);
            $('#date').text(response['createdAt']);
            // $('#like').text(response['trip']['like']);

            // 자신이 작성한 리뷰에만 수정/삭제 버튼 뜨게 한다
            if (response['user']['username'] == localStorage.getItem('username')) {
                $('#own-check').show();
            } else {
                $('#own-check').hide();
            }
        }
    });
}

// 댓글 달기
function postUserReview(id) {
    let UserReviewComment = $('#comment_content').val();
    if (UserReviewComment != null) {
        $.ajax({
            type: "POST",
            url: `/userReview/comment/${id}`,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({comment: UserReviewComment}),
            statusCode: {
                401: () => { // 로그인 안 하고 댓글 작성 시
                    alert('로그인이 필요한 서비스입니다.');
                    window.location.href = "../templates/login.html";
                }
            },
            success: function (response) {
                window.location.reload();
            }
        });
    } else {
        alert('댓글을 입력해주세요!')
    }
}

// 리뷰 수정 화면에서 input 창에 이전 데이터 값 보이게 함
function updateUserReview(id) {
    $.ajax({
        type: "GET",
        url: `/userReview/${id}`,
        success: function (response) {
            sessionStorage.setItem('title', response['title']);
            sessionStorage.setItem('place', response['place']);
            sessionStorage.setItem('review', response['review']);
            sessionStorage.setItem('file', response['reviewImgUrl']);

            window.location.href = `../templates/tripUpdate.html?id=${id}`;
        }
    });
}

function showComments() {
    $('#comment_list').empty();
    $.ajax({
        type: "GET",
        url: `/userReview/comment/${getId()}`,
        data: {},
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let comment_id = response[i]['id'];
                let profile_img = response[i]['user']['profileImgUrl'];
                let nickname = response[i]['user']['nickname'];
                let comment = response[i]['comment'];
                let date = new Date(response[i]['createdAt']);
                let date_before = time2str(date);

                let html_temp = `<div class="mb-3">
                                    <div class="d-flex justify-content-between">
                                        <div class="d-flex align-items-center">
                                            <img src="${profile_img}" width="35px" height="35px" style="object-fit: cover; border-radius: 50%;" />
                                            <span style="margin-left: 5px; font-size: 15px; font-weight: 700;">${nickname}</span>
                                            <span style="margin-left: 5px; font-size: 13px;">${date_before}</span>
                                        </div>
                                        <a id="${comment_id}" href="javascript:deleteComment(${comment_id})" style="display: none;"><i class="fas fa-trash-alt" style="color: #6E85B2;"></i></a>
                                    </div>
                                    <div style="margin: 5px 0 0 5px; word-break:break-all; font-size: 14px; font-weight: 400;">${comment}</div>
                                 </div>`;
                $('#comment_list').append(html_temp);

                // 로그인한 유저와 댓글을 쓴 유저가 같으면 삭제 아이콘이 뜸
                if (response[i]['user']['username'] === localStorage.getItem('username')) {
                    $(`#${comment_id}`).css('display', 'block');
                } else {
                    $(`#${comment_id}`).css('display', 'none');
                }
            }
        }
    });
}

// // 댓글 삭제
// function deleteComment(comment_id) {
//     $.ajax({
//         type: "DELETE",
//         url: `/userReview/comment/${comment_id}/${}`,
//         data: {comment_id: comment_id},
//         success: function (response) {
//             if (response['result'] == 'success') {
//                 alert('댓글 삭제 완료!');
//                 showComments();
//             }
//         }
//     });
// }

// 리뷰 삭제
function deleteUserReview(id) {
    $.ajax({
        type: "DELETE",
        url: `/userReview/delete/${id}`,
        data: {},
        success: function (response) {
            alert('삭제 완료');
            window.location.href = "../templates/tripsList.html";
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
                url: "/trips/like",
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
                url: "/trips/like",
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

function get_like(id) {
    $.ajax({
        type: "GET",
        url: `/trips/like/${id}`,
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


// 카카오톡 공유하기
function kakaoShare() {
    $.ajax({
        type: "GET",
        url: `/userReview/${getId()}`,
        data: {},
        success: function (response) {
            let share_title = response['title'];
            let share_place = response['place'];
            let share_img = response['reviewImgUrl'];
            let share_like = 0; // 좋아요 기능 완료되면 수정
            let share_comment_count = 0; // 댓글 기능 완료되면 수정

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

