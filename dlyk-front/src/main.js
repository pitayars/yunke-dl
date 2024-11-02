//语法结构：import ... from ... 结构，从依赖中的vue框架导入createApp()函数
import { createApp } from 'vue'

//从依赖中的element-plus框架导入ElementPlus组件
import ElementPlus from 'element-plus'

//从依赖的element-plus框架导入css样式，导入样式不需要from
import 'element-plus/dist/index.css'

//从element-plus/icons-vue导入所有的图标组件
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

//从element-plus/dist/locale/zh-cn.mjs导入zhCn中文组件
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

//从router.js这个文件导入router组件
import router from './router/router'

//从./App.vue这个页面导入App组件（vue页面本身也叫组件，组件名默认是页面的文件名）
import App from './App.vue'
//import permission from "@/util/permission";
import {jwtName} from "./util/utils";


//解决缩小或放大页面时，页面报错---start
const debounce = (fn, delay) => {
    let timer = null;
    return function () {
        let context = this;
        let args = arguments;
        clearTimeout(timer);
        timer = setTimeout(function () {
            fn.apply(context, args);
        }, delay);
    }
}
const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver{
    constructor(callback) {
        callback = debounce(callback, 16);
        super(callback);
    }
}
//解决缩小或放大页面时，页面报错---end


//使用上面导入的createApp()函数创建vue应用，创建vue应用时需要给它一个vue页面/组件，不能凭空创建
let app = createApp(App);

//在创建的vue应用中使用ElementPlus组件
app.use(ElementPlus, {locale: zhCn});

//循环出所有的图标，注册到vue应用中
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

//在创建的vue应用中使用router组件
app.use(router);

app.directive('hasPermission', (el, binding) => {
    if (!permissionCheck(binding.value)) {
        //el.removeChild(el);
        //el.parentNode && el.parentNode.removeChild(el)
        el.style.display='none';
    }
    function permissionCheck(value){
        let token = window.sessionStorage.getItem(jwtName());
        if (!token) {
            token = window.localStorage.getItem(jwtName());
        }
        let tokenObject = JSON.parse(token);
        for (let item of tokenObject.stringAuthorityList) {
            console.log(item)
            if (item === value) {
                return true;
            }
        }
        return false;
    }
})

//把创建的vue应用挂载到index.html页面div元素id=app的位置下
app.mount('#app');