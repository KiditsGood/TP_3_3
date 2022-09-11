import Vue from 'vue'
import VueRouter from 'vue-router'
import AppMain from "@/views/AppMain";
import AppProfile from "@/views/AppProfile";
import SignIn from "@/views/SignIn";
import SignUp from "@/views/SignUp";
import AppCatalog from "@/views/AppCatalog";
import CatalogVegetables from "@/views/CatalogVegetables";

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
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
