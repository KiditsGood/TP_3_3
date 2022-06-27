getCookie('token')
if (getCookie('token') != null) {
    window.location.replace('index.html')
}


$('.auth--button').click(function () {
    let email = $('#email').val()
    let password = $('#password').val()
    let usersData = JSON.stringify({
        email,
        password
    })

    sendRequest('https://tp3-3.herokuapp.com/users/auth', usersData, 'POST', {'Content-Type': 'application/json'}, function (data) {
        setCookie('token', data.token, {expires: 3600})
        window.location.replace('index.html')
    })

})
