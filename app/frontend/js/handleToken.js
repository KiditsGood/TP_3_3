const TOKEN = getCookie('token')

$(document).ready(function () {
    sendRequest('https://tp3-3.herokuapp.com/users/get_user', '', 'GET', {'Content-Type': 'application/json'}, function (data) {
        if (TOKEN != null) {
            $('.header--item').last().html(data.email)
            console.log(TOKEN)
        }
    })
})
