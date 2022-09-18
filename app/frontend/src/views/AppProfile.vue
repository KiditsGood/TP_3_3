<template>
    <div>
        <AppHeader />
        <div class="main">
            <div class="profile">
                <div class="profile--left">
                    <div class="profile__item">
                        <p class="profile__item-title">Фамилия:</p>
                        <input readonly v-model="surname" id="profile--surname" class="profile__item-value">
                    </div>
                    <div class="profile__item">
                        <p class="profile__item-title">Имя:</p>
                        <input readonly v-model="name" id="profile--name" class="profile__item-value">
                    </div>
                    <div class="profile__item">
                        <p class="profile__item-title">Отчество:</p>
                        <input readonly v-model="patronymic" id="profile--patronymic" class="profile__item-value">
                    </div>
                    <div class="profile__item">
                        <p class="profile__item-title">Дата рождения:</p>
                        <input readonly v-model="birthday" type="date" id="profile--birthday" class="profile__item-value">
                    </div>
                    <div class="profile__item">
                        <p class="profile__item-title">Номер телефона:</p>
                        <input readonly v-model="phone" id="profile--phone" class="profile__item-value">
                    </div>
                    <button @click="onEdit" type="button" class="profile__edit">Редактировать</button>
                </div>
                <form @submit.prevent="addRecipe" class="profile__right">
                    <div class="profile__right-list">
                        <div class="profile__right-flex">
                            <p class="profile__right-name">Название рецепта</p>
                            <input v-model="recipeName" class="profile__right-input" type="text">
                        </div>
                        <div class="profile__right-flex">
                            <p class="profile__right-name">Описание рецепта</p>
                            <input v-model="recipeDesc" class="profile__right-input" type="text">
                        </div>
                        <div class="profile__right-flex">
                            <p class="profile__right-name">Фото рецепта</p>
                            <input @change="recipePhoto" class="profile__right-input" type="file">
                        </div>
                        <div class="profile__right-flex">
                            <p class="profile__right-name">Поиск по продуктам</p>
                            <input v-model="recipeSearch" class="profile__right-input" type="search" placeholder="Введите название продукта">
                            <div v-show="selectedElems.length > 0">
                                <p class="profile__right-name">Выбранные продукты:</p>
                                <p class="profile__right-name" v-for="selectElem in selectedElems" :selectElem="selectElem">{{ selectElem.name }}</p>
                            </div>
                            <div v-show="(recipeSearch.length >= 2)" class="profile__right-flex--search">
                                <div v-for="searchElem in searchObject" :searchElem="searchElem" :key="searchElem.id" class="selected">
                                    <input type="checkbox" :id="searchElem.id" :value="searchElem" v-model="selectedElems">
                                    <label :for="searchElem.id" class="search__item"> {{ searchElem.name }}</label>
                                </div>

                            </div>
                        </div>
                        <button class="profile__right-button" type="submit">Добавить рецепт</button>
                    </div>
                    <div class="profile__right-list">

                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
    import AppHeader from "@/components/AppHeader";
    import {mapGetters} from "vuex";
    import {AuthAPI} from "@/axios";

    export default {
        name: "AppProfile",
        components: {AppHeader},

        computed: {
            ...mapGetters(['user']),

            searchObject() {
                return this.searchElems.filter(searchElem => searchElem.name.toLowerCase().includes(this.recipeSearch.toLowerCase()))
            }
        },
        async mounted() {
            await this.$store.dispatch('getUser')

            this.surname = this.user.lastName
            this.name = this.user.firstName
            this.patronymic = this.user.patronymic
            this.birthday = this.user.birthday
            this.phone = this.user.phoneNumber
            this.email = this.user.email

            const searchResp = await AuthAPI.get('products/all')

            this.searchElems = searchResp.data
        },

        data() {
            return {
                surname: '',
                name: '',
                patronymic: '',
                birthday: '',
                phone: '',
                email: '',
                profileButton: '',
                recipeName: '',
                recipeDesc: '',
                recipePhoto: '',
                recipeSearch: '',
                searchElems: [],
                selectedElems: []
            }
        },

        methods: {
            onEdit() {
                this.profileButton = document.querySelector('.profile__edit')

                this.profileButton.onclick = async () => {
                    document.querySelectorAll('.profile__item-value').forEach(el => {

                        if (el.hasAttribute('readonly')) {
                            el.removeAttribute('readonly')
                            this.profileButton.innerHTML = 'Сохранить'
                        }
                        else {
                            el.setAttribute('readonly', '')
                            this.profileButton.innerHTML = 'Редактировать'
                        }
                    })

                    if (this.profileButton.textContent == 'Сохранить') {

                    }
                    else {
                        const getUser = await AuthAPI.get('users/get_user')

                        await AuthAPI.put('users', {
                            lastName: this.surname,
                            firstName: this.name,
                            patronymic: this.patronymic,
                            birthday: this.birthday,
                            phoneNumber: this.phone,
                            email: this.user.email,
                            password: this.user.password,
                            favouriteProducts: getUser.data.favouriteProducts,
                            favouriteRecipes: getUser.data.favouriteRecipes,
                            productCarts: getUser.data.productCarts,
                            id: this.user.id,
                        })

                        const updUser = await AuthAPI.get('users/getUser')


                        this.$cookies.remove('user')
                        this.$cookies.set('user', updUser.data)
                    }
                }
            },

            async addRecipe() {
                await AuthAPI.post('recipes', {
                    creator: this.user.id,
                    name: this.recipeName,
                    description: this.recipeDesc,
                    products: this.selectedElems
                })
                this.recipeName = ''
                this.recipeDesc = ''
                this.selectedElems = ''
            },
        }
    }
</script>

<style lang="scss" scoped>
    .profile{
        display: flex;
        align-items: flex-start;
        justify-content: space-between;

        &__right{
            display: flex;
            flex-direction: column;
            gap: 30px;

            &-list{
                display: flex;
                flex-direction: column;
                gap: 10px;
                outline: 3px solid #009900;
                border-radius: 50px;
                padding: 25px;
            }

            &-input{
                outline: 2px solid #336633;
                border-radius: 15px;
                padding: 5px 10px;
                box-sizing: border-box;
                height: 40px;
                font-weight: 500;
                font-size: 16px;
            }

            &-flex{
                display: flex;
                flex-direction: column;
                gap: 5px;
                position: relative;

                &--search{
                    display: flex;
                    flex-direction: column;
                    gap: 10px;
                    box-sizing: border-box;
                    width: 100%;
                    position: absolute;
                    background: white;
                    outline: 2px solid #336633;
                    padding: 5px;
                    top: 75px;
                }
            }

            &-name{
                font-weight: 500;
                color: #336633;
            }

            &-button{
                font-size: 16px;
                line-height: 16px;
                font-weight: 600;
                color: white;
                background: #009900;
                transition: 0.2s;
                border-radius: 15px;
                padding: 15px 30px;
                margin-top: 10px;

                &:hover{
                    color: #009900;
                    outline: 1px solid #009900;
                    background: none;
                    cursor: pointer;
                }
            }
        }
    }
    .profile--left{
        display: flex;
        flex-direction: column;
        gap: 15px;
        align-items: flex-start;
        max-width: 420px;
    }
    .profile__item{
        display: flex;
        align-items: center;
        gap: 50px;

        &-title{
            font-size: 16px;
            line-height: 16px;
            font-weight: 600;
            width: 150px;
        }

        &-value{
            outline: 1px solid #009900;
            padding: 5px 10px;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 500;
            line-height: 16px;
        }
    }
    .profile__edit{
        margin: 25px auto;
        font-size: 16px;
        line-height: 16px;
        font-weight: 500;
        color: #009900;
        outline: 2px solid #009900;
        background: none;
        border-radius: 15px;
        padding: 10px 0;
        transition: 0.2s;
        width: 100%;

        &:hover{
            cursor: pointer;
            background: #009900;
            color: white;
            outline: none;
        }
    }
</style>