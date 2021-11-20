function navLoginCheck() {
    if (localStorage.getItem('token')) {
        $('#login-btn').hide();
        $('#profile-btn').show();
    } else {
        $('#login-btn').show();
        $('#profile-btn').hide();
    }
}