import axios from 'axios';

export default {

  createAccount(userAccount) {
    return axios.post('/account/create', userAccount)
  },

  getAccount(userId) {
    return axios.get(`/account/access/${userId}`)
  }
}