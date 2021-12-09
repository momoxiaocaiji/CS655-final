// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import md5 from 'js-md5'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';


Vue.config.productionTip = false
Vue.prototype.$md5 = md5
Vue.use(ElementUI);

/* eslint-disable no-new */
new Vue({
  el: '#app1',
  components: { App },
  template: '<App/>'
})
