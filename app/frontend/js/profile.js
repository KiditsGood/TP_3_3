$(document).ready(function () {
    let profileSurname = $('#profile--surname')
    let profileName = $('#profile--name')
    let profilePatronymic = $('#profile--patronymic')
    let profileBirthday = $('#profile--birthday')
    let profilePhone = $('#profile--phone')
    let profileEmail = $('#profile--email')

    sendRequest('https://tp3-3.herokuapp.com/users/get_user', '', 'GET', {'Content-Type': 'application/json'}, function (data) {
        profileSurname.val(data.lastName)
        profileName.val(data.firstName)
        profilePatronymic.val(data.patronymic)
        profileBirthday.val(data.birthday)
        profilePhone.val(data.phoneNumber)
        profileEmail.val(data.email)
        console.log(data)

        $('.profile__edit').click(function () {
            if($('.profile__item-value').attr('disabled')) {
                $(this).html('Сохранить')

                $('.profile__item-value').removeAttr('disabled')
            }
            else {
                let profileData = JSON.stringify({
                    lastName: profileSurname.val(),
                    firstName: profileName.val(),
                    patronymic: profilePatronymic.val(),
                    birthday: profileBirthday.val(),
                    phoneNumber: profilePhone.val(),
                    email: profileEmail.val(),
                    favouriteProducts: data.favouriteProducts,
                    favouriteRecipes: data.favouriteRecipes,
                    productCarts: data.productCarts,
                    id: data.id,
                    password: data.password
                })

                sendRequest('https://tp3-3.herokuapp.com/users', profileData, 'PUT', {'Content-Type': 'application/json'}, function (data) {})

                $(this).html('Редактировать')

                $('.profile__item-value').attr('disabled', '')
            }
        })
    })
})
