<template>
    <div class="main">
        <form @submit.prevent="logIn" class="auth">
            <a href="/" class="header__left-logo"><img src="../assets/static/img/products.svg"/>PROSHOP</a>
            <input v-model="email" id="email" type="email" maxlength="15" class="auth--item" placeholder="Введите e-mail..."/>
            <input v-model="password" id="password" type="password" class="auth--item" placeholder="Введите пароль..."/>
            <button type="submit" class="auth--button">Войти</button>
            <a class="auth--already" href="/sign_up">Нет аккаунта? Зарегистрируйтесь</a>
        </form>
    </div>
</template>

<script>
    import {mapMutations} from "vuex";
    import { API } from "@/axios";

    export default {
        name: "SignIn",
        beforeCreate() {
            if (this.$store.state.user !== null) this.$router.push('/')
        },
        data() {
            return {
                email: '',
                password: ''
            }
        },
        methods: {
            ...mapMutations(['setUser', 'setAccessToken']),
            async logIn() {
                const res = await API.post('users/auth', {
                    email: this.email,
                    password: this.password
                })
                this.$cookies.set('access-token', res.data.token) // заносим токен в куки
                this.setAccessToken(res.data.token)
                const user = await API.get('users/getUser', {
                    headers: {
                        'Authorization':'Bearer ' + res.data.token
                    }
                })
                this.setUser(user.data)
                this.$cookies.set('user', user.data)
                this.$router.push('/')
            }
        }
    }
</script>

<style lang="scss" scoped>
    .auth{
        max-width: 500px;
        margin: 0 auto;
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 15px;

        &--item{
            border: 2px solid #336633;
            box-shadow: 0 0 0 1px lightgray;
            border-radius: 25px;
            padding: 10px 20px;
            width: calc(100% - 200px);
            font-size: 16px;
            line-height: 16px;
            font-weight: 500;
        }

        &--button{
            font-size: 20px;
            font-weight: 500;
            line-height: 20px;
            background: white;
            outline: 2px solid #336633;
            color: #336633;
            padding: 10px 20px;
            border-radius: 15px;
            width: calc(100% - 250px);
            margin-top: 15px;
            transition: 0.2s;

            &:hover{
                background: #336633;
                outline: none;
                color: white;
                cursor: pointer;
            }
        }

        &--already{
            color: gray;
            font-size: 14px;
            font-weight: 500;
            line-height: 14px;
            text-decoration: underline;
            margin-top: 10px;
        }
    }
    .header__left-logo{
        font-size: 36px;
        font-weight: 700;
        color: #336633;
        display: flex;
        align-items: center;
        gap: 10px;
        width: 36px;
    }
</style>