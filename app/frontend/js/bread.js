$(document).ready(function() {
    $.ajax({
        method: 'GET',
        url: 'https://tp3-3.herokuapp.com/products',
        dataType: 'json',
        success: data => {

            console.log(data)
            $('.loading').remove()

            data.items.forEach(el => {
                if(el.category == "Хлеб, выпечка") {
                    console.log(el)
                    $('.main__products').append(`
                            <div class="products__item">
                                <svg class="products__item-like" width="24" height="24" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg" color="#E5E6E8"><path d="M19.1 7.25a5.06 5.06 0 00-4.96-4.75c-1.73 0-3.25.98-4.14 2.38A4.97 4.97 0 005.86 2.5 5.05 5.05 0 00.9 7.25c-.82 4.7 6.02 8.5 9.1 11.08 3.09-2.58 9.9-6.37 9.1-11.08z" fill="#E5E6E8"></path></svg>
                                <img class="products__item-image" src="${el.mainPhoto}"/>
                                <p class="products__item-title">${el.name}</p>
                                <div class="products__desc">
                                    <a onclick="descWatcher(this)" class="products__item-watch">Подробнее</a>
                                    <p class="products__item-description">${el.description}</p>
                                </div>
                                <div class="products__item-cost--flex">
                                    <p class="products__item-cost">${el.price}</p>
                                    <button type="button" class="products__item-buy">Купить</button>
                                </div>
                            </div>  
                        `)
                }
            })


        }
    })
})

function descWatcher(event) {
    $(event).next().slideToggle(100)
}
