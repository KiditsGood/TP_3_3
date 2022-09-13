<template>
  <div>
      <AppHeader />
      <div class="main">
          <div class="recipe__field">
              <div class="recipe__field-items">
                  <RecipeCard v-for="recipe in recipes" :recipe="recipe" :key="recipe.id"/>
              </div>
          </div>
      </div>
      
  </div>
</template>

<script>
import AppHeader from "@/components/AppHeader";
import {AuthAPI} from "@/axios";
import RecipeCard from "@/components/RecipeCard";
export default {
    name: "AppRecipes",
    components: {RecipeCard, AppHeader},

    data() {
      return {
          recipes: []
      }
    },
    

    methods: {
      async fetchRecipes() {
          const recipesResp = await AuthAPI.get('recipes/all')
          this.recipes = recipesResp.data

          console.log(recipesResp.data)
      }
    },

    mounted() {
        this.fetchRecipes()
    }
}
</script>

<style lang="scss" scoped>
    .recipe__field{
        
        &-items{
            display: flex;
            align-items: flex-start;
            justify-content: space-between;
            flex-wrap: wrap;
            gap: 20px;
        }
        
    }
</style>