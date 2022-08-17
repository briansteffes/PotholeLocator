import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));
const currentUserAccount = JSON.parse(localStorage.getItem('userAccount'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    userAccount: currentUserAccount || {},
    potholes: [],
    images: {
      1: 'https://assets.dmagstatic.com/wp-content/uploads/2015/03/texas-pothole.jpg',
      2: 'https://www.cityworks.com/wp-content/uploads/2020/01/pot-hole-blog.gif',
      3: 'https://asphaltpavingdallastx.com/wp-content/uploads/2021/02/pothole-repair-dallas-5-1.jpg',
      4: 'https://www.grangeinsurance.com/-/media/images/tips/grange/potholes1200x630.jpg',
      5: 'https://lichfieldlive.co.uk/wp-content/uploads/2020/03/pothole-1200x900.jpg',
      6: 'https://ewscripps.brightspotcdn.com/dims4/default/d7c67e1/2147483647/strip/true/crop/855x481+7+0/resize/1280x720!/quality/90/?url=http%3A%2F%2Fewscripps-brightspot.s3.amazonaws.com%2F03%2F43%2F1013a15e4ee5929448b4ca4dcb4d%2Fpothole.jpg',
      7: 'https://www.abc27.com/wp-content/uploads/sites/55/2021/03/potholes-formation-pkg.00_00_01_08.Still001.jpg',
      8: 'https://pavingfinder.com/wp-content/uploads/2021/02/pothole-782x423.jpg',
      9: 'https://www.grangeinsurance.com/-/media/images/tips/grange/potholes1200x630.jpg',
      10: 'https://www.lonestarpavingtx.com/wp-content/uploads/2016/07/large-pothole-in-road-.jpg',
      11: 'https://www.blisstowingservice.com/wp-content/uploads/2021/11/road-terrible-condition-with-many-potholes-bumps-car-moving-along-it-1200x675.jpg',
      12: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTjlWqGZuDo2sRqFrrN03b793OFuMib2hQfRw&usqp=CAU',
      13: 'https://www.abc27.com/wp-content/uploads/sites/55/2015/03/potholefile_39108805_ver1.0.jpg',
      14: 'https://s.yimg.com/ny/api/res/1.2/fr9BK7gvgISnaUbRmG0VQg--/YXBwaWQ9aGlnaGxhbmRlcjt3PTY0MDtoPTM1OA--/https://s.yimg.com/uu/api/res/1.2/Mb5avk8t48phNZYnRUsp7Q--~B/aD0yMTA1O3c9Mzc2MTthcHBpZD15dGFjaHlvbg--/https://media.zenfs.com/en/las-cruces-sun-news/328ca9d885e2921969ac1d1834d677b5',
      15: 'http://content.cbs19.tv/photo/2015/03/19/7188518_G_3713725_ver1.0.jpg',
      16: 'https://cdn-ds.com/blogs-media/sites/618/2017/05/27205602/pothole-on-road-miami-lakes-automall.jpg',
      17: 'https://arc-anglerfish-arc2-prod-gmg.s3.amazonaws.com/public/FRBMRSIHBJAGZOZL5VXVKP42TM.jpg',
      18: 'https://www.colonyoneauto.com/wp-content/uploads/2020/03/shutterstock_577628641.jpg',
      19: 'https://townsquare.media/site/466/files/2022/03/attachment-Potholes.jpg?w=980&q=75',
      20: 'https://cdn.vox-cdn.com/thumbor/szCfjxJMWn95CNAvRFPOipkh7_A=/0x0:1920x1080/1200x0/filters:focal(0x0:1920x1080):no_upscale()/cdn.vox-cdn.com/uploads/chorus_asset/file/12865897/01_DOTV8131000H_Paving_for_Pizza_30_1920x1080x1_vdf16.1001.jpg',
      21: 'https://st2.depositphotos.com/1002626/8385/i/600/depositphotos_83858840-stock-photo-road-with-potholes-and-puddles.jpg',
      22: 'https://9b16f79ca967fd0708d1-2713572fef44aa49ec323e813b06d2d9.ssl.cf2.rackcdn.com/1140x_a10-7_cTC/20200212emPotholes02-1-1582068901.jpg'
  },
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    SET_USER_ACCOUNT(state, userAccount) {
      state.userAccount = userAccount;
      localStorage.setItem('userAccount', JSON.stringify(userAccount));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      localStorage.removeItem('userAccount');
      state.token = '';
      state.user = {};
      state.userAccount = {};
      axios.defaults.headers.common = {};
    },
    SET_POTHOLES(state, data) {
      state.potholes = data;
    }
  }
})
