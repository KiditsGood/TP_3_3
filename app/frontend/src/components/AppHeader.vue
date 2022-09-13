<template>
    <header class="header">
        <div class="header__left">
            <a href="/" class="header__left-logo"><img src="../assets/static/img/products.svg"/>PROSHOP</a>
            <router-link to="/catalog" class="header--item"><img class="svg" src="../assets/static/img/catalog.svg"/>Каталог</router-link>
        </div>
        <div class="header__right">
          <router-link to="/recipes" class="header--item">
            <img src="../assets/static/img/recipes.svg" alt="" class="svg">
            Рецепты
          </router-link>
          <router-link to="/recipes" class="header--item">
            <img src="../assets/static/img/cart.svg" alt="" class="svg">
            Корзина
          </router-link>
            <div class="header__out" v-if="user !== null">
                <a href="/profile" class="header--item"><img class="svg" src="../assets/static/img/user.svg"/>{{ user.email }}</a>
                <a @click="logout">Выйти</a>
            </div>

            <a href="/sign_in" class="header--item" v-else><img class="svg" src="../assets/static/img/user.svg"/>Войти</a>
        </div>
    </header>
</template>

<script>
    import {mapGetters, mapMutations} from 'vuex'

    export default {
        name: "AppHeader",

        data() {
          return {

          }
        },

        computed: {
            ...mapGetters(['user'])
        },
        beforeCreate() {
            if (this.$store.state.user === null) this.$router.push('/sign_in')
        },

        async mounted() {
            await this.$store.dispatch('getUser')
        },

        methods: {
            ...mapMutations(['logOutUser']),
            logout() {
                this.logOutUser()
                this.$router.go('/')
            }
        }
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

        &__out{
            display: flex;
            align-items: center;
            gap: 10px;

            & a:last-child{
                color: #009900;
                text-decoration: underline;
                font-size: 16px;
                font-weight: 500;
            }
        }

        &__right{
          display: flex;
          align-items: center;
          gap: 30px;
        }
    }
    .svg{
        width: 18px;
    }
</style>