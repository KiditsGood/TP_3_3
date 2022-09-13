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
                    <div class="profile__item">
                        <p class="profile__item-title">E-Mail:</p>
                        <input readonly v-model="email" id="profile--email" class="profile__item-value">
                    </div>
                    <button @click="onEdit" type="button" class="profile__edit">Редактировать</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import AppHeader from "@/components/AppHeader";
    import {mapGetters} from "vuex";
    import {AuthAPI} from "@/axios";
    import Vue from "vue";
    export default {
        name: "AppProfile",
        components: {AppHeader},

        computed: {
            ...mapGetters(['user'])
        },
        async mounted() {
            await this.$store.dispatch('getUser')

            this.surname = this.user.lastName
            this.name = this.user.firstName
            this.patronymic = this.user.patronymic
            this.birthday = this.user.birthday
            this.phone = this.user.phoneNumber
            this.email = this.user.email
        },

        data() {
            return {
                surname: '',
                name: '',
                patronymic: '',
                birthday: '',
                phone: '',
                email: '',
                profileButton: ''
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
                        await AuthAPI.put('users', {
                            lastName: this.surname,
                            firstName: this.name,
                            patronymic: this.patronymic,
                            birthday: this.birthday,
                            phoneNumber: this.phone,
                            email: this.email,
                            password: this.user.password,
                            favouriteProducts: this.user.favouriteProducts,
                            favouriteRecipes: this.user.favouriteRecipes,
                            productCarts: this.user.productCarts,
                            id: this.user.id,
                        })

                        const updUser = await AuthAPI.get('users/getUser')


                        this.$cookies.remove('user')
                        this.$cookies.set('user', updUser.data)
                    }
                }
            }
        }
    }
</script>

<style lang="scss" scoped>
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