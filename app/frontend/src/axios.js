import axios from 'axios';
import Vue from 'vue';
import VueCookies from 'vue-cookies';

Vue.use(VueCookies)

export const API = axios.create({
    baseURL: 'https://tp3-3.herokuapp.com/', // урл от апи
})

export default axios.create({
    baseURL: 'https://tp3-3.herokuapp.com/', // урл от апи
    headers: {
        'Authorization' : 'Bearer ' + Vue.$cookies.get('access-token')
    }
})
