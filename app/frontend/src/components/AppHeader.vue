<template>
    <header class="header">
        <div class="header__left">
            <a href="/" class="header__left-logo"><img src="../assets/static/img/products.svg"/>PROSHOP</a>
            <a class="header--item"><img class="svg" src="../assets/static/img/catalog.svg"/>Каталог</a>
        </div>
        <div class="header__right">
            <a class="header--item"><img class="svg" src="../assets/static/img/user.svg"/>Войти</a>
        </div>
    </header>
</template>

<script>
    export default {
        name: "AppHeader"
    }
</script>

<style lang="scss" scoped>
    .header{
        display: flex;
        align-items: center;
        justify-content: space-between;
        gap: 10px;
        background: white;
        padding: 25px 220px;
        box-shadow: rgb(241 243 244) 0 4px 18px;

        &__left{
            display: flex;
            align-items: center;
            justify-content: space-between;
            width: 400px;

            &-logo{
                font-size: 36px;
                font-weight: 700;
                color: #336633;
                display: flex;
                align-items: center;
                gap: 10px;
                width: 36px;
            }
        }

        &--item{
            font-size: 18px;
            font-weight: 500;
            color: #009900;
            display: flex;
            align-items: center;
            gap: 5px;
        }
    }
    .svg{
        width: 18px;
    }
</style>