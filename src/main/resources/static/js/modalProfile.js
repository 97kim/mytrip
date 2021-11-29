function saveProfile() {
    let modal_nickname = $('#modal_nickname').val();
    let modal_img = $('#modal_img')[0].files[0];

    let form_data = new FormData();

    form_data.append("nickname", modal_nickname);
    form_data.append("profileImgUrl", modal_img);

    $.ajax({
        type: "POST",
        url: "/profile",
        data: form_data,
        contentType: false,
        processData: false,
        success: function (response) {
            alert('프로필 업데이트 완료!');
            window.location.reload();
        }
    });
}