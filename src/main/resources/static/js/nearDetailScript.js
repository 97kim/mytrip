function getId() {
    const URLSearch = new URLSearchParams(location.search);
    return URLSearch.get('id');
}

function getDetailIntro() {
    $.ajax({
        type: "GET",
        url: `/near/place/intro/${getId()}`,
        data: {},
        async: false,
        success: function (response) {
            response = JSON.parse(response);
            $('#title').text(response['title']);
            if (response['firstimage']) {
                $('#file').attr('src', response['firstimage']);
            } else {
                $('#file').attr('src', "https://dk9q1cr2zzfmc.cloudfront.net/img/noImage.png");
            }
            $('#address').text(response['addr1']);
            $('#overview').html(response['overview']);
            if (response['homepage']) {
                $('#homepage').html(response['homepage']);
            } else {
                $('#homepage').text('');
            }
            if (!response['mapy'] || !response['mapx']) {
                response['mapy'] = 0;
                response['mapx'] = 0;
            }

            sessionStorage.setItem('near_place_lat', response['mapy']);
            sessionStorage.setItem('near_place_lng', response['mapx']);
        }
    });
}

function getMap() {
    let map = new naver.maps.Map('map', {
        center: new naver.maps.LatLng(
            Number(sessionStorage.getItem('near_place_lat')),
            Number(sessionStorage.getItem('near_place_lng'))
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
            Number(sessionStorage.getItem('near_place_lat')),
            Number(sessionStorage.getItem('near_place_lng'))
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

function weather() {
    let place_lat = sessionStorage.getItem('near_place_lat')
    let place_lng = sessionStorage.getItem('near_place_lng')

    $.ajax({
        type: "POST",
        url: '/near/place/weather',
        contentType: "application/json",
        data: JSON.stringify({
            place_lat: place_lat,
            place_lng: place_lng
        }),
        async: false,
        success: function (response) {
            response = JSON.parse(response);
            let icon = response['weather'][0]['icon'];
            let weather = response['weather'][0]['main'];
            let temp = response['main']['temp'];
            temp = Number(temp).toFixed(1); //소수점 둘째자리에서 반올림해 첫째자리까지 표현
            let location = response['name'];
            if (weather == 'Rain') {
                let rain = response['rain']['1h'];
                $('#rain').text(rain + 'mm/h');
            }
            let wind = response['wind']['speed'];

            $('#icon').attr('src', `https://openweathermap.org/img/w/${icon}.png`);
            $('#location').text(location);
            $('#weather').text(weather);
            $('#temp').text(temp + '°C');
            $('#wind').text(wind + 'm/s');
        }
    });
}

function toggle_bookmark(content_id) {
    let title = $('#title').text();
    let address = $('#address').text();
    let file = $('#file').attr('src');

    if (!localStorage.getItem('token')) {
        alert('로그인이 필요한 서비스입니다.')
        window.location.href = "../templates/login.html"
    } else {
        if ($('#bookmark').hasClass("fas")) {
            $.ajax({
                type: "POST",
                url: "/near/place/bookmark",
                contentType: "application/json",
                data: JSON.stringify({
                    content_id: content_id,
                    action: "uncheck",
                    type: "near",
                    title: title,
                    address: address,
                    img_url: file,
                    user_id: localStorage.getItem("userId")
                }),
                success: function (response) {
                    $('#bookmark').removeClass("fas").addClass("far")
                }
            });
        } else {
            $.ajax({
                type: "POST",
                url: "/near/place/bookmark",
                contentType: "application/json",
                data: JSON.stringify({
                    content_id: content_id,
                    action: "check",
                    type: "near",
                    title: title,
                    address: address,
                    img_url: file,
                    user_id: localStorage.getItem("userId")
                }),
                success: function (response) {
                    $('#bookmark').removeClass("far").addClass("fas")
                }
            });

        }
    }
}

function getBookmark() {
    $.ajax({
        type: "POST",
        url: `/near/place/bookmark/${getId()}`,
        contentType: 'application/json',
        data: JSON.stringify({
            user_id: localStorage.getItem('userId')
        }),
        success: function (response) {
            if (response['bookmarkStatus'] == true) {
                $('#bookmark').removeClass("far").addClass("fas");
            } else {
                $('#bookmark').removeClass("fas").addClass("far")
            }
        }
    });
}
