import Vue from 'vue'
import VueRouter from 'vue-router'
import AppMain from "@/views/AppMain";
import AppProfile from "@/views/AppProfile";
import SignIn from "@/views/SignIn";
import SignUp from "@/views/SignUp";
import AppCatalog from "@/views/AppCatalog";
import CatalogVegetables from "@/views/CatalogVegetables";
import CatalogBread from "@/views/CatalogBread";
import CatalogDrinks from "@/views/CatalogDrinks";
import CatalogMeet from "@/views/CatalogMeet";
import AppRecipes from "@/views/AppRecipes";
import AppFavourite from "@/views/AppFavourite";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Main',
    component: AppMain
  },
  {
    path: '/profile',
    name: 'Profile',
    component: AppProfile
  },
  {
    path: '/sign_in',
    name: 'Auth',
    component: SignIn
  },
  {
    path: '/sign_up',
    name: 'Reg',
    component: SignUp
  },
  {
    path: '/catalog',
    name: 'Catalog',
    component: AppCatalog
  },
  {
    path: '/catalog/vegetables',
    name: 'Vegetables',
    component: CatalogVegetables
  },
  {
    path: '/catalog/bread',
    name: 'Bread',
    component: CatalogBread
  },
  {
    path: '/catalog/drinks',
    name: 'Drinks',
    component: CatalogDrinks
  },
  {
    path: '/catalog/meet',
    name: 'Meet',
    component: CatalogMeet
  },
  {
    path: '/recipes',
    name: 'Recipes',
    component: AppRecipes
  },
  {
    path: '/favourite',
    name: 'Favourite',
    component: AppFavourite
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
