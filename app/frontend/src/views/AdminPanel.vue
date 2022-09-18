<template>
    <div>
        <AppHeader />
        <div class="main">
            <div class="main__edit">
                <form @submit.prevent="createProduct" class="edit">
                    <p class="edit__title">Новый товар</p>
                    <div class="edit__item">
                        <p class="edit__item-name">Название товара</p>
                        <input v-model="createName" type="text" class="edit__item-input" placeholder="Введите название">
                    </div>
                    <div class="edit__item">
                        <p class="edit__item-name">Описание товара</p>
                        <input v-model="createDesc" type="text" class="edit__item-input" placeholder="Введите описание">
                    </div>
                    <div class="edit__item">
                        <p class="edit__item-name">Категория товара</p>
                        <select class="edit__item-input" v-model="createCat">
                            <option disabled selected>Выберите из списка</option>
                            <option>Напитки</option>
                            <option>Мясо, птица</option>
                            <option>Хлеб, выпечка</option>
                            <option>Овощи</option>
                        </select>
                    </div>
                    <div class="edit__item">
                        <p class="edit__item-name">Цена товара</p>
                        <input v-model="createPrice" type="number" class="edit__item-input" placeholder="Введите цену">
                    </div>
                    <button type="submit" class="edit__button">Создать</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
    import AppHeader from "@/components/AppHeader";
    import {AuthAPI} from "@/axios";

    export default {
        name: "AdminPanel",
        components: {AppHeader},
        async beforeCreate() {
            const user = (this.$cookies.get('user'))
            console.log(user)
            if (user.userRole === 'ROLE_USER') {
                this.$router.push('/')
            }
        },

        data() {
            return {
                createName: '',
                createDesc: '',
                createCat: '',
                createPrice: ''
            }
        },

        methods: {
            async createProduct() {
                await AuthAPI.post('products', {
                    name: this.createName,
                    description: this.createDesc,
                    category: this.createCat,
                    price: this.createPrice
                })
            }
        }
    }
</script>

<style lang="scss" scoped>
    .main__edit{
        max-width: 1480px;
        margin: 0 auto;
        display: flex;
        align-items: flex-start;
        gap: 30px;
        flex-wrap: wrap;
    }

    .edit{
        border: 3px solid #009900;
        box-shadow: 0 0 0 1px lightgrey;
        width: 450px;
        border-radius: 50px;
        display: flex;
        flex-direction: column;
        gap: 30px;
        padding: 25px 20px;

        &__title{
            font-size: 24px;
            color: #336633;
            font-weight: 600;
            text-align: center;
            text-transform: uppercase;
        }

        &__item{
            display: flex;
            flex-direction: column;
            gap: 5px;

            &-name{
                font-weight: 500;
                font-size: 18px;
            }

            &-input{
                border: 2px solid #009900;
                border-radius: 20px;
                padding: 5px 10px;
                font-size: 16px;
                font-weight: 500;
                line-height: 16px;
            }
        }

        &__button{
            font-size: 16px;
            line-height: 16px;
            font-weight: 600;
            color: white;
            background: #009900;
            transition: 0.2s;
            border-radius: 15px;
            padding: 15px 30px;

            &:hover{
                color: #009900;
                outline: 1px solid #009900;
                background: none;
                cursor: pointer;
            }
        }
    }
</style>