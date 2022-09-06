import axios from 'axios';

export const API = axios.create({
    baseURL: 'https://tp3-3.herokuapp.com/', // урл от апи
})
