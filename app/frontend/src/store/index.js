import Vue from 'vue'
import Vuex from 'vuex'
import { API } from "@/axios";
import VueCookies from 'vue-cookies'

Vue.use(Vuex)
Vue.use(VueCookies)

export default new Vuex.Store({
  state: {
    accessToken: Vue.$cookies.get('access-token') ? Vue.$cookies.get('access-token') : null, // смотрим есть ли токен авторизации в куки
    user: Vue.$cookies.get('user') ? Vue.$cookies.get('user') : null, // смотрим есть ли инстанс юзера в куки
    dataFetched: true // хук чтобы понять надо ли показывать лоадер
  },
  getters: {
    user(state) {
      return state.user
    }
  },
  mutations: {
    setUser(state, user) {
      state.user = user
    },
    setFetchedState(state, bool) {
      state.dataFetched = bool
    },
    setAccessToken(state, token) {
      state.accessToken = token
    },
    logOutUser(state) {
      Vue.$cookies.remove('Authorization') // убираем куки авторизации
      Vue.$cookies.remove('user') // убираем куки юзера
      state.accessToken = null
      state.user = null
    }
  },
  actions: {
    async getUser({ commit, state }) {
      if (state.accessToken === null) return // если юзер не залогинен - не отправляем
      try {
        commit('setFetchedState', false)
        const response = await API.get('users/get_user', {
          headers: {
            'Authorization': 'Bearer ' + state.accessToken
          }
        })
        commit('setUser', response.data)
      }
      catch (err) {
        return err
      }
      finally {
        setTimeout(() => {commit('setFetchedState', true)}, 2500)
      }
    }
  },
  modules: {
  }
})
