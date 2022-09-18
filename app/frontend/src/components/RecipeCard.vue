<template>
    <div class="recipe">
        <svg v-if="!checkFav" @click="favoriteHandler" class="products__item-like" width="24" height="24" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg" color="#E5E6E8"><path d="M19.1 7.25a5.06 5.06 0 00-4.96-4.75c-1.73 0-3.25.98-4.14 2.38A4.97 4.97 0 005.86 2.5 5.05 5.05 0 00.9 7.25c-.82 4.7 6.02 8.5 9.1 11.08 3.09-2.58 9.9-6.37 9.1-11.08z" fill="#E5E6E8"></path></svg>
        <svg v-else @click="favoriteHandler" class="products__item-like" width="24" height="24" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg" color="#E5E6E8"><path d="M19.1 7.25a5.06 5.06 0 00-4.96-4.75c-1.73 0-3.25.98-4.14 2.38A4.97 4.97 0 005.86 2.5 5.05 5.05 0 00.9 7.25c-.82 4.7 6.02 8.5 9.1 11.08 3.09-2.58 9.9-6.37 9.1-11.08z" fill="red"></path></svg>
        <img src="" alt="" class="recipe__photo">
        <p class="recipe__name">{{ recipe.name }}</p>
        <p class="recipe__description">{{ recipe.description }}</p>
        <div class="recipe__products">
            <p v-for="rec in recipeProducts" class="recipe__products-item" :rec="rec">{{ rec.name }}</p>
        </div>
    </div>
</template>

<script>
import {AuthAPI} from "@/axios";

export default {
    name: "RecipeCard",
    
    data() {
      return {
          recipeProducts: this.recipe.products,
          isFavourite: false,
          checkFav: false
      }
    },

    methods: {
        async favoriteHandler() {
            const favResp = await AuthAPI.get('users/get_user')

            let favProducts = favResp.data.favouriteRecipes

            this.isFavourite = false

            for (let i = 0; i < favProducts.length; i++){
                if (favProducts[i].id === this.recipe.id) {
                    this.isFavourite = true
                }
            }

            if (this.isFavourite) {
                favProducts = favProducts.filter((el) => {return el.id !== this.recipe.id})

                this.checkFav = false
            }
            else {
                favProducts.push(this.recipe)

                this.checkFav = true
            }

            await AuthAPI.put('users', {
                lastName: favResp.data.lastName,
                firstName: favResp.data.firstName,
                patronymic: favResp.data.patronymic,
                birthday: favResp.data.birthday,
                phoneNumber: favResp.data.phoneNumber,
                email: favResp.data.email,
                favouriteProducts: favResp.data.favouriteProducts,
                favouriteRecipes: favProducts,
                productCarts: favResp.data.productCarts,
                id: favResp.data.id,
            })

        },

        async favoriteChecker() {
            const checkerResp = await AuthAPI.get('users/get_user')

            for (let i = 0; i < checkerResp.data.favouriteRecipes.length; i++) {
                if (checkerResp.data.favouriteRecipes[i].id === this.recipe.id) {
                    this.checkFav = true
                }
            }
        },
    },
    
    props: {
        recipe: {
            type: Object
        }
    },

    mounted() {
        this.favoriteChecker()
    }
}
</script>

<style lang="scss" scoped>
    .products__item-like{
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

    .recipe{
        max-width: 300px;
        min-width: 300px;
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 10px;
        padding: 15px 25px;
        box-sizing: border-box;
        border-radius: 30px;
        border: 3px solid #336633;
        position: relative;
        
        &__photo{
            max-width: 250px;
        }
        
        &__name{
            font-size: 18px;
            color: #336633;
            font-weight: 600;
        }
        
        &__description{
            font-size: 14px;
            color: lightblue;
            font-weight: 500;
            width: 100%;
        }
        
        &__creator{
            font-size: 16px;
            color: #009900;
            font-weight: 700;
            
            &:before{
                content: 'Автор: ';
            }
        }
        
        &__products{
            display: flex;
            align-items: center;
            gap: 10px;
            flex-wrap: wrap;
            justify-content: space-between;
            
            &-item{
                font-size: 16px;
                font-weight: 500;
                color: #009900;
            }
        }
    }
</style>