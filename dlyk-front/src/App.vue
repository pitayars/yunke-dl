<!--第一部分：html代码-->
<template>
  <!--该标签的内容来自于路由页面-->
  <!--那就看你访问的地址，你访问 /， 那么此处就渲染显示 @/view/LoginView.vue这个页面-->
  <!--你访问 /dashboard， 那么此处就渲染显示 @/view/DashboardView.vue这个页面-->
  <router-view/>
</template>

<!--第二部分：javascript代码-->
<script>
export default {
  //定义组件名，如果没有定义组件名，默认是vue页面的文件名
  name: 'App',
}
</script>

<!--第三部分：css代码-->
<style>
</style>
