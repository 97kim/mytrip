// 리뷰 작성
function update() {
    let trip_id = sessionStorage.getItem('trip_id');
    let title = $('#title').val();
    let place = $('#place').val();
    let review = $('#review').val();
    let file = $('#file')[0].files[0];

    let form_data = new FormData();

    form_data.append("file_give", file);
    form_data.append("title_give", title);
    form_data.append("place_give", place);
    form_data.append("review_give", review);

    $.ajax({
        type: "PUT",
        url: `/trips/place/${trip_id}`,
        data: form_data,
        cache: false,
        contentType: false,
        processData: false,
        success: function (response) {
            alert(response["msg"])
            window.location.href = '../templates/tripsDetail.html';
            // sessionStorage.setItem("trip_id", trip_id);
        }
    });
}

// 파일 업로더 js
function readURL(input) {
    if (input.files && input.files[0]) {

        var reader = new FileReader();

        reader.onload = function (e) {
            $('.image-upload-wrap').hide();

            $('.file-upload-image').attr('src', e.target.result);
            $('.file-upload-content').show();

            $('.image-title').html(input.files[0].name);
        };

        reader.readAsDataURL(input.files[0]);

    } else {
        removeUpload();
    }
}

function removeUpload() {
    $('.file-upload-input').replaceWith($('.file-upload-input').clone());
    $('.file-upload-content').hide();
    $('.image-upload-wrap').show();
}

$('.image-upload-wrap').bind('dragover', function () {
    $('.image-upload-wrap').addClass('image-dropping');
});
$('.image-upload-wrap').bind('dragleave', function () {
    $('.image-upload-wrap').removeClass('image-dropping');
});