// 导出一个对象用作自定义指令的第二个参数
export default {
    mounted(el, binding) {
        this.checkPermission(el, binding)
    },
    updated(el, binding) {
        this.checkPermission(el, binding)
    },

    methods: {
        // el就是指令所在的 元素（element）   我们可以使用  el.style.dispaly='none' 来对el 的css进行设置
        //binding 是指的binding 的信息， 其中可以得到我们 v-hasProm = value 中的  value值
        //vnode 就是指的虚拟节点， 我们可以通过它， 得到当前页面中的  component.data 中的数据 也就是promission 数组长  页面的 this 在指令中是得不到了， 我们能过  vnode.context得到的就是 this
        checkPermission(el, binding) {
            // 获取自定义指令传过来的数组（binding.value）
            const btnRoles = binding.value
            // 取一下本地存的账号权限
            const userRoles = JSON.parse(localStorage.getItem("role"))

            // 判断自定义指令的传值，在账号权限数组中能否找到
            if (btnRoles && btnRoles instanceof Array) {
                if (btnRoles.length) {
                    // 能找到返回true
                    const hasPermission = userRoles.some(v => {
                        return btnRoles.includes(v)
                    })
                    // 找不到返回false，使用自定义指令的钩子函数，操作dom元素删除该节点
                    if (!hasPermission) {
                        el.parentNode && el.parentNode.removeChild(el)
                    }
                } else {
                    throw new Error(`权限参数异常`)
                }
            }
        }
    }
}