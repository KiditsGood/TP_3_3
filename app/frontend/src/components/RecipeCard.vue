<template>
    <div class="recipe">
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
          recipeProducts: this.recipe.products
      }
    },
    
    props: {
        recipe: {
            type: Object
        }
    }
}
</script>

<style lang="scss" scoped>
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