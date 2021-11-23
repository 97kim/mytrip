$.ajaxPrefilter(function (options, originalOptions, jqXHR) {
    if (localStorage.getItem('token')) {
        jqXHR.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));
    }
});