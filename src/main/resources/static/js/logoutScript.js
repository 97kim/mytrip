function logoutScript() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    alert('로그아웃을 완료했습니다.');
    window.location.href = '/';
}