<template>
    <div>
        <AppHeader />
        <div class="main">
            <div class="main__flex">
                <div class="main__products">
                    <ProductCart v-for="cartProduct in cartProducts" :cartProduct="cartProduct" :key="cartProduct.id"/>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import AppHeader from "@/components/AppHeader";
    import {AuthAPI} from "@/axios";
    import ProductCart from "@/components/ProductCart";
    export default {
        name: "AppCart",
        components: {ProductCart, AppHeader},

        data() {
            return {
                cartProducts: []
            }
        },

        methods: {
            async cartHandler() {
                const cartResp = await AuthAPI.get('users/get_user')

                const cartSize = cartResp.data.productCarts.length

                for (let i = 0; i < cartSize; i++) {
                    const prodResp = await AuthAPI.get('products/' + cartResp.data.productCarts[i].id.productId)

                    this.cartProducts.push({
                        description: prodResp.data.description,
                        name: prodResp.data.name,
                        price: prodResp.data.price,
                        amount: cartResp.data.productCarts[i].amount,
                        id: cartResp.data.productCarts[i].id.productId
                    })
                }
            }
        },

        mounted() {
            this.cartHandler()
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

    .main__products{
        display: flex;
        align-items: flex-start;
        gap: 10px;
    }
</style>