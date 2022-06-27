$('.auth--button').click(function () {
    let lastName = $('#lastName').val()
    let firstName = $('#firstName').val()
    let patronymic = $('#patronymic').val()
    let birthday = $('#birthday').val()
    let phoneNumber = $('#phoneNumber').val()
    let email = $('#email').val()
    let password = $('#password').val()

    $.ajax({
        type: 'POST',
        url: 'https://tp3-3.herokuapp.com/users',
        dataType: "json",
        data: JSON.stringify({
            lastName,
            firstName,
            patronymic,
            birthday,
            phoneNumber,
            email,
            password
        }),
        contentType: 'application/json'
    })
})
