$(document).ready(function () {
    sendRequest('https://tp3-3.herokuapp.com/users/get_user', '', 'GET', {'Content-Type': 'application/json'}, function (data) {
        let bestData = JSON.stringify({
            lastName: data.lastName,
            firstName: data.firstName,
            patronymic: data.patronymic,
            birthday: data.birthday,
            phoneNumber: data.phoneNumber,
            email: data.email,
            favouriteProducts: [],
            favouriteRecipes: [],
            productCarts: [],
            id: data.id
        })

        $('.products__item-like').click(function () {
            sendRequest('https://tp3-3.herokuapp.com/users', bestData, 'PUT', {'Content-Type': 'application/json'}, function (favData) {})
        })
    })

})