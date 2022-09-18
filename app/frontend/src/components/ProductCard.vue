<template>
    <div class="products__item">
        <svg v-if="!checkFav" @click="favoriteHandler" class="products__item-like" width="24" height="24" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg" color="#E5E6E8"><path d="M19.1 7.25a5.06 5.06 0 00-4.96-4.75c-1.73 0-3.25.98-4.14 2.38A4.97 4.97 0 005.86 2.5 5.05 5.05 0 00.9 7.25c-.82 4.7 6.02 8.5 9.1 11.08 3.09-2.58 9.9-6.37 9.1-11.08z" fill="#E5E6E8"></path></svg>
        <svg v-else @click="favoriteHandler" class="products__item-like" width="24" height="24" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg" color="#E5E6E8"><path d="M19.1 7.25a5.06 5.06 0 00-4.96-4.75c-1.73 0-3.25.98-4.14 2.38A4.97 4.97 0 005.86 2.5 5.05 5.05 0 00.9 7.25c-.82 4.7 6.02 8.5 9.1 11.08 3.09-2.58 9.9-6.37 9.1-11.08z" fill="red"></path></svg>
        <img class="products__item-image" src=""/>
        <p class="products__item-title">{{ product.name }}</p>
        <div class="products__desc">
            <a @click="show = !show" class="products__item-watch">Подробнее</a>
            <transition name="fade">
                <p v-if="show" class="products__item-description">{{ product.description }}</p>
            </transition>
        </div>
        <div class="products__item-cost--flex">
            <p class="products__item-cost">{{ product.price }}</p>
            <input class="products__item-amount" v-model="cartAmount" type="number" />
            <button @click="buyHandler" type="button" class="products__item-buy">Купить</button>
        </div>
    </div>
</template>

<script>
    import {AuthAPI} from "@/axios";
    import {mapGetters} from "vuex";

    export default {
        name: "ProductCard",
        
        computed: {
          ...mapGetters(["user"])
        },
        
        data() {
            return {
                isFavourite: false,
                checkFav: false,
                show: false,
                cartAmount: 1
            }
        },

        props: {
            product: {
                type: Object,
                required: true
            }
        },
        
        methods: {
            async favoriteHandler() {
                const favResp = await AuthAPI.get('users/get_user')

                let favProducts = favResp.data.favouriteProducts

                this.isFavourite = false

                for (let i = 0; i < favProducts.length; i++){
                    if (favProducts[i].id === this.product.id) {
                        this.isFavourite = true
                    }
                }

                if (this.isFavourite) {
                    favProducts = favProducts.filter((el) => {return el.id !== this.product.id})

                    this.checkFav = false
                }
                else {
                    favProducts.push(this.product)

                    this.checkFav = true
                }

                await AuthAPI.put('users', {
                    lastName: favResp.data.lastName,
                    firstName: favResp.data.firstName,
                    patronymic: favResp.data.patronymic,
                    birthday: favResp.data.birthday,
                    phoneNumber: favResp.data.phoneNumber,
                    email: favResp.data.email,
                    favouriteProducts: favProducts,
                    favouriteRecipes: favResp.data.favouriteRecipes,
                    productCarts: favResp.data.productCarts,
                    id: favResp.data.id,
                })
                
            },

            async favoriteChecker() {
                const checkerResp = await AuthAPI.get('users/get_user')

                for (let i = 0; i < checkerResp.data.favouriteProducts.length; i++) {
                    if (checkerResp.data.favouriteProducts[i].id === this.product.id) {
                        this.checkFav = true
                    }
                }
            },

            async buyHandler(e) {
                let buyElement = e.target

                buyElement.innerHTML = 'В корзине'
                buyElement.classList.add('active')

                setTimeout(() => {
                    buyElement.innerHTML = 'Купить'
                    buyElement.classList.remove('active')
                }, 1500)

                const cartResp = await AuthAPI.get('users/get_user')

                cartResp.data.productCarts.push({
                    id: {
                        userId: this.user.id,
                        productId: this.product.id
                    },
                    price: this.product.price,
                    amount: this.cartAmount
                })

                await AuthAPI.put('users', {
                    lastName: cartResp.data.lastName,
                    firstName: cartResp.data.firstName,
                    patronymic: cartResp.data.patronymic,
                    birthday: cartResp.data.birthday,
                    phoneNumber: cartResp.data.phoneNumber,
                    email: cartResp.data.email,
                    favouriteProducts: cartResp.data.favouriteProducts,
                    favouriteRecipes: cartResp.data.favouriteRecipes,
                    productCarts: cartResp.data.productCarts,
                    id: cartResp.data.id,
                })
            }
        },

        mounted() {
            this.favoriteChecker()
        }
    }

</script>

<style lang="scss" scoped>
    .products__item{
        display: flex;
        flex-direction: column;
        gap: 10px;
        border: 3px solid #336633;
        border-radius: 30px;
        padding: 15px 25px;
        width: 250px;
        position: relative;

        &-image{
            width: 150px;
            border-radius: 30px;
        }

        &-title{
            font-size: 20px;
            line-height: 20px;
            font-weight: 500;
            height: 40px;
        }

        &-description{
            font-size: 16px;
            line-height: 16px;
            font-weight: 400;
            color: lightgray;
        }

        &-cost{
            font-size: 18px;
            line-height: 18px;
            font-weight: 500;
            color: #336633;

            &--flex{
                display: flex;
                align-items: center;
                justify-content: space-between;
                margin-top: 30px;
            }
        }

        &-buy{
            font-size: 16px;
            font-weight: 500;
            line-height: 15px;
            color: #009900;
            border: 1px solid #009900;
            border-radius: 10px;
            padding: 10px;
            transition: 0.2s;
            box-sizing: border-box;
            width: 130px;

            &:hover{
                opacity: 0.5;
                cursor: pointer;
            }

            &.active{
                background: #009900;
                color: white;
            }
        }

        &-like{
            position: absolute;
            transition: 0.2s;
            top: 15px;
            right: 16px;

            & path{
                transition: 0.2s;
            }

            &:hover path{
                fill: red;
                cursor: pointer;
            }
        }

        &-watch{
            font-size: 14px;
            font-weight: 500;
            line-height: 14px;
            color: gray;
            text-decoration: underline;
        }

        &-amount{
            outline: 1px solid #009900;
            width: 20%;
            border-radius: 10px;
            box-sizing: border-box;
            font-size: 18px;
            font-weight: 500;
            color: #336633;
            text-align: center;

            &::-webkit-outer-spin-button, &::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }
        }
    }

    .products__desc{
        display: flex;
        flex-direction: column;
        gap: 10px;
    }

    .fade-enter-active, .fade-leave-active {
        transition: opacity .5s;
    }

    .fade-enter, .fade-leave-to /* .fade-leave-active до версии 2.1.8 */ {
        opacity: 0;
    }
</style>