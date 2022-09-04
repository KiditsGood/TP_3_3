import { createRouter, createWebHistory } from 'vue-router'
import AppMain from "@/views/AppMain";
import AppProfile from "@/views/AppProfile";
import SignIn from "@/views/SignIn";
import SignUp from "@/views/SignUp";

const routes = [
  {
    path: '/',
    name: 'AppMain',
    component: AppMain
  },
  {
    path: '/profile',
    name: 'AppProfile',
    component: AppProfile
  },
  {
    path: '/sign_in',
    name: 'SignIn',
    component: SignIn
  },
  {
    path: '/sign_up',
    name: 'SignUp',
    component: SignUp
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
