<template>
    <AppHeader />
    <div class="main">
        <div class="profile">
            <div class="profile--left">
                <div class="profile__item">
                    <p class="profile__item-title">Фамилия:</p>
                    <input :value="surname" @input="surname = $event.target.value" id="profile--surname" class="profile__item-value" value="">
                </div>
                <div class="profile__item">
                    <p class="profile__item-title">Имя:</p>
                    <input :value="name" @input="name = $event.target.value" id="profile--name" class="profile__item-value" value="">
                </div>
                <div class="profile__item">
                    <p class="profile__item-title">Отчество:</p>
                    <input :value="patronymic" @input="patronymic = $event.target.value" id="profile--patronymic" class="profile__item-value" value="">
                </div>
                <div class="profile__item">
                    <p class="profile__item-title">Дата рождения:</p>
                    <input :value="birthday" @input="birthday = $event.target.value" type="date" id="profile--birthday" class="profile__item-value" value="">
                </div>
                <div class="profile__item">
                    <p class="profile__item-title">Номер телефона:</p>
                    <input :value="phone" @input="phone = $event.target.value" id="profile--phone" class="profile__item-value" value="">
                </div>
                <div class="profile__item">
                    <p class="profile__item-title">E-Mail:</p>
                    <input :value="email" @input="email = $event.target.value" id="profile--email" class="profile__item-value" value="">
                </div>
                <button type="button" class="profile__edit">Редактировать</button>
            </div>
        </div>
    </div>
</template>

<script>
    import AppHeader from "@/components/AppHeader";
    export default {
        name: "AppProfile",
        components: {AppHeader},

        data() {
            return {
                surname: '',
                name: '',
                patronymic: '',
                birthday: '',
                phone: '',
                email: ''
            }
        },

        methods: {

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
</style>