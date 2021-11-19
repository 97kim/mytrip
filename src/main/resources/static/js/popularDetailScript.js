function getId_popular() {
    const URLSearch = new URLSearchParams(location.search);
    return URLSearch.get('id');
}

function getDetailIntro() {
    $.ajax({
        type: "POST",
        url: '/popular/place/intro',
        data: {
            content_id_give: getId_popular(),
        },
        async: false,
        success: function (response) {
            let detail_intro_list = response['detail_intro_list'];
            $('#title').text(detail_intro_list['title']);
            $('#file').attr('src', detail_intro_list['firstimage'])
            $('#overview').html(detail_intro_list['overview']);
            if (detail_intro_list['homepage']) {
                $('#homepage').html(detail_intro_list['homepage']);
            } else {
                $('#homepage').text('');
            }
            if (!detail_intro_list['mapy'] || !detail_intro_list['mapx']) {
                detail_intro_list['mapy'] = 0;
                detail_intro_list['mapx'] = 0;
            }

            sessionStorage.setItem('popular_place_lat', detail_intro_list['mapy']);
            sessionStorage.setItem('popular_place_lng', detail_intro_list['mapx']);
        }
    });
}


function getMap_popular() {
    let map = new naver.maps.Map('map', {
        center: new naver.maps.LatLng(
            Number(sessionStorage.getItem('popular_place_lat')),
            Number(sessionStorage.getItem('popular_place_lng'))
        ),
        zoom: 16,
        zoomControl: true,
        zoomControlOptions: {
            style: naver.maps.ZoomControlStyle.SMALL,
            position: naver.maps.Position.TOP_RIGHT
        }
    });

    let marker = new naver.maps.Marker({
        position: new naver.maps.LatLng(
            Number(sessionStorage.getItem('popular_place_lat')),
            Number(sessionStorage.getItem('popular_place_lng'))
        ),
        map: map,
        icon: {
            content: '<img src="https://dk9q1cr2zzfmc.cloudfront.net/img/marker.png">',
            anchor: new naver.maps.Point(20, 25)
        }
    });

    let infowindow = new naver.maps.InfoWindow({
        content: `<div style="width: 50px;height: 20px;text-align: center"><h5>here!</h5></div>`,
    });

    naver.maps.Event.addListener(marker, "click", function () {
        // infowindow.getMap(): 정보창이 열려있을 때는 연결된 지도를 반환하고 닫혀있을 때는 null을 반환
        if (infowindow.getMap()) {
            infowindow.close();
        } else {
            infowindow.open(map, marker);
        }
    });
}

function weather_popular() {
    let place_lat = sessionStorage.getItem('popular_place_lat');
    let place_lng = sessionStorage.getItem('popular_place_lng');

    $.ajax({
        type: "POST",
        url: '/popular/place/weather',
        data: {
            place_lat: place_lat,
            place_lng: place_lng
        },
        async: false,
        success: function (response) {
            let icon = response['weather_info_popular']['weather'][0]['icon'];
            let weather = response['weather_info_popular']['weather'][0]['main'];
            let temp = response['weather_info_popular']['main']['temp'];
            temp = Number(temp).toFixed(1); //소수점 둘째자리에서 반올림해 첫째자리까지 표현
            let location = response['weather_info_popular']['name'];
            if (weather == 'Rain') {
                let rain = response['weather_info_popular']['rain']['1h'];
                $('#rain').text(rain + 'mm/h');
            }
            let wind = response['weather_info_popular']['wind']['speed'];

            $('#icon').attr('src', `https://openweathermap.org/img/w/${icon}.png`);
            $('#location').text(location);
            $('#weather').text(weather);
            $('#temp').text(temp + '°C');
            $('#wind').text(wind + 'm/s');
        }
    });
}

function toggle_bookmark_popular(content_id) {
    let title = $('#title').text();
    let file = $('#file').attr('src');

    if (!localStorage.getItem('token')) {
        alert('로그인이 필요한 서비스입니다.')
        window.location.href = "../templates/login.html"
    } else {
        if ($('#bookmark').hasClass("fas")) {
            $.ajax({
                type: "POST",
                url: "/popular/place/bookmark",
                data: {
                    content_id_give: content_id,
                    action_give: "uncheck",
                    title_give: title,
                    file_give: file
                },
                success: function (response) {
                    if (response['result'] == 'success') {
                        $('#bookmark').removeClass("fas").addClass("far")
                    }
                }
            })
        } else {
            $.ajax({
                type: "POST",
                url: "/popular/place/bookmark",
                data: {
                    content_id_give: content_id,
                    action_give: "check",
                    title_give: title,
                    file_give: file
                },
                success: function (response) {
                    if (response['result'] == 'success') {
                        $('#bookmark').removeClass("far").addClass("fas")
                    }
                }
            });

        }
    }
}

function getBookmark_popular() {
    $.ajax({
        type: "GET",
        url: `/popular/place/bookmark/${getId_popular()}`,
        data: {},
        success: function (response) {
            if (response['bookmark_status'] == true) {
                $('#bookmark').removeClass("far").addClass("fas");
            } else {
                $('#bookmark').removeClass("fas").addClass("far")
            }
        }
    });
}