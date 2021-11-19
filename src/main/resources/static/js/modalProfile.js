function saveProfile() {
    let modal_nickname = $('#modal_nickname').val();
    let modal_img = $('#modal_img')[0].files[0];

    let form_data = new FormData();

    form_data.append("nickname_give", modal_nickname);
    form_data.append("img_give", modal_img);

    $.ajax({
        type: "POST",
        url: "/profile",
        data: form_data,
        cache: false,
        contentType: false,
        processData: false,
        success: function (response) {
            alert(response["msg"])
            window.location.reload();
        }
    });
}