let searchValue = ''

$(window).on('load', categoryPage())

function categoryPage () {
    $('.main__products').html('')
    $('.main__pagination').html('')

    $.ajax({
        method: 'GET',
        url: 'https://tp3-3.herokuapp.com/products?search=name:*' + searchValue + '*,category:Овощи&page_number=0&page_size=4',
        dataType: 'json',
        success: paginationData => {
            console.log(paginationData)
            for (let i = 1; i <= Math.ceil(paginationData.totalElements / 4); i++) {
                $('.main__pagination').append(
                    '<a onclick="paginationItem(this)" class="main__pagination-item">' + i + '</a>'
                )
            }
            paginationData.items.forEach(el => {
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
            })
        }
    })
}

$('.main__search-button').click(function () {
    searchValue = $('.main__search-input').val()
    categoryPage()
})

function descWatcher(event) {
    $(event).next().slideToggle(100)
}

function paginationItem(paginationNum){
    let pageNumber = $(paginationNum).text() - 1
    console.log(searchValue)
    console.log(pageNumber)
    $('.main__products').html('')

    $.ajax({
        method: 'GET',
        url: 'https://tp3-3.herokuapp.com/products?search=name:*' + searchValue + '*,category:Овощи&page_number=' + pageNumber + '&page_size=4',
        dataType: 'json',
        success: searchData => {
            console.log(searchData)

            searchData.items.forEach(el => {
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
            })
        }
    })

}

