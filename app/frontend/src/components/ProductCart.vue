<template>
    <div class="products__item">
<!--        <svg v-if="!checkFav" @click="favoriteHandler" class="products__item-like" width="24" height="24" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg" color="#E5E6E8"><path d="M19.1 7.25a5.06 5.06 0 00-4.96-4.75c-1.73 0-3.25.98-4.14 2.38A4.97 4.97 0 005.86 2.5 5.05 5.05 0 00.9 7.25c-.82 4.7 6.02 8.5 9.1 11.08 3.09-2.58 9.9-6.37 9.1-11.08z" fill="#E5E6E8"></path></svg>-->
<!--        <svg v-else @click="favoriteHandler" class="products__item-like" width="24" height="24" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg" color="#E5E6E8"><path d="M19.1 7.25a5.06 5.06 0 00-4.96-4.75c-1.73 0-3.25.98-4.14 2.38A4.97 4.97 0 005.86 2.5 5.05 5.05 0 00.9 7.25c-.82 4.7 6.02 8.5 9.1 11.08 3.09-2.58 9.9-6.37 9.1-11.08z" fill="red"></path></svg>-->
        <img class="products__item-image" src=""/>
        <p class="products__item-title">{{ cartProduct.name }}</p>
        <div class="products__desc">
            <a @click="show = !show" class="products__item-watch">Подробнее</a>
            <transition name="fade">
                <p v-if="show" class="products__item-description">{{ cartProduct.description }}</p>
            </transition>
        </div>
        <div class="products__item-cost--flex">
            <p class="products__item-cost">{{ (cartProduct.price * cartAmount).toFixed(2) }}</p>
            <div class="products__item-col">
                <p @click="minusProduct">-</p>
                <input readonly class="products__item-amount" v-model="cartAmount" type="number" />
                <p @click="plusProduct">+</p>
            </div>

            <button @click="deleteHandler" type="button" class="products__item-buy">Удалить</button>
        </div>
    </div>
</template>

<script>
    import {AuthAPI} from "@/axios";
    import {mapGetters} from "vuex";

    export default {
        name: "ProductCart",

        computed: {
            ...mapGetters(["user"])
        },

        data() {
            return {
                isFavourite: false,
                checkFav: false,
                show: false,
                cartAmount: this.cartProduct.amount,
                userInfo: ''
            }
        },

        props: {
            cartProduct: {
                type: Object,
                required: true
            }
        },

        methods: {
            async minusProduct() {
                this.cartAmount -= 1

                if (this.cartAmount <= 1) {
                    this.cartAmount = 1
                }

                let cartSize = this.userInfo.data.productCarts.length

                for (let i = 0; i < cartSize; i++) {
                    if (this.userInfo.data.productCarts[i].id.productId === this.cartProduct.id) {
                        this.userInfo.data.productCarts[i].amount = this.cartAmount
                    }
                }

                await AuthAPI.put('users', {
                    lastName: this.userInfo.data.lastName,
                    firstName: this.userInfo.data.firstName,
                    patronymic: this.userInfo.data.patronymic,
                    birthday: this.userInfo.data.birthday,
                    phoneNumber: this.userInfo.data.phoneNumber,
                    email: this.userInfo.data.email,
                    favouriteProducts: this.userInfo.data.favouriteProducts,
                    favouriteRecipes: this.userInfo.data.favouriteRecipes,
                    productCarts: this.userInfo.data.productCarts,
                    id: this.userInfo.data.id,
                })
            },

            async plusProduct() {
                this.cartAmount += 1

                if (this.cartAmount >= 999) {
                    this.cartAmount = 999
                }

                let cartSize = this.userInfo.data.productCarts.length

                for (let i = 0; i < cartSize; i++) {
                    if (this.userInfo.data.productCarts[i].id.productId === this.cartProduct.id) {
                        this.userInfo.data.productCarts[i].amount = this.cartAmount
                    }
                }

                await AuthAPI.put('users', {
                    lastName: this.userInfo.data.lastName,
                    firstName: this.userInfo.data.firstName,
                    patronymic: this.userInfo.data.patronymic,
                    birthday: this.userInfo.data.birthday,
                    phoneNumber: this.userInfo.data.phoneNumber,
                    email: this.userInfo.data.email,
                    favouriteProducts: this.userInfo.data.favouriteProducts,
                    favouriteRecipes: this.userInfo.data.favouriteRecipes,
                    productCarts: this.userInfo.data.productCarts,
                    id: this.userInfo.data.id,
                })
            },

            async deleteHandler() {
                await AuthAPI.put('users', {
                    lastName: this.userInfo.data.lastName,
                    firstName: this.userInfo.data.firstName,
                    patronymic: this.userInfo.data.patronymic,
                    birthday: this.userInfo.data.birthday,
                    phoneNumber: this.userInfo.data.phoneNumber,
                    email: this.userInfo.data.email,
                    favouriteProducts: this.userInfo.data.favouriteProducts,
                    favouriteRecipes: this.userInfo.data.favouriteRecipes,
                    productCarts: this.userInfo.data.productCarts.filter((el) => {return el.id.productId !== this.cartProduct.id}),
                    id: this.userInfo.data.id,
                })
            }
        },

        async mounted() {
             this.userInfo = await AuthAPI.get('users/get_user')
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
            width: 50%;
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

        &-col{
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 5px;
            width: 100%;

            & p{
                font-size: 18px;
                font-weight: 500;
                
                &:hover{
                    cursor: pointer;
                }
                
                &:nth-child(2):hover {
                    cursor: default;
                }
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