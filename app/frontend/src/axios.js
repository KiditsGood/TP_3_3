import axios from 'axios';
import Vue from 'vue';
import VueCookies from 'vue-cookies'

Vue.use(VueCookies)

export const API = axios.create({
    baseURL: 'https://tp3-3.herokuapp.com/'
})

export const AuthAPI = axios.create({
    baseURL: 'https://tp3-3.herokuapp.com/',
    headers: {
        'Authorization': 'Bearer ' + Vue.$cookies.get('access-token')
    }
})
