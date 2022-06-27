let flag = 0

$('.cart__item-value--input--plus').click(function () {
    flag += 1

    $('.cart__item-value--input').val(flag)
})