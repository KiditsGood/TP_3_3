import axios from "axios";

axios.defaults.baseURL = 'https://tp3-3.herokuapp.com/'
axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('token')