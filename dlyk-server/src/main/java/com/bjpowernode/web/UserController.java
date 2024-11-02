package com.bjpowernode.web;

import com.bjpowernode.domain.po.TPermission;
import com.bjpowernode.domain.po.TUser;
import com.bjpowernode.domain.query.UserQuery;
import com.bjpowernode.domain.result.R;
import com.bjpowernode.service.UserService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 这个接口是一个空结果，直接返回成功即可
     *
     * @return
     */
    @GetMapping(value = "/api/freeLogin")
    public R freeLogin() {
        return R.OK();
    }

    /**
     * 获取当前登录人的信息
     *
     * @param authentication
     * @return
     */
    @GetMapping(value = "/api/login/info")
    public R loginInfo(Authentication authentication) {
        return R.OK(authentication);
    }

    /**
     * 用户数据分页查询
     *
     * @param current
     * @return
     */
    @GetMapping(value = "/api/users")
    public R userPage(@RequestParam(value = "current", required = false) Integer current) {
        // required = false, 表示current可以传也可以不传；
        // required = true, 表示current必须传，不传就报400错误
        if (current == null) {
            current = 1;
        }

        PageInfo<TUser> pageInfo = userService.getUserByPage(current);
        return R.OK(pageInfo);
    }

    /**
     * 根据id查询用户详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/api/user/{id}")
    public R userDetail(@PathVariable(value = "id") Integer id) {
        TUser tUser = userService.getUserById(id);

        return R.OK(tUser);
    }

    /**
     * 新增用户提交保存
     *
     * @param userQuery
     * @return
     */
    @PostMapping(value = "/api/user")
    public R addUser(UserQuery userQuery, @RequestHeader(value = "Authorization") String token) {
        //前端是通过 FormData 提交过来的参数，怎么接收，方法如下：
        //1、request.getParamter("xxx");
        //2、@RequestParam(value="xxx")
        //3、UserQuery对象接收（只要表单input框里面的字段名和UserQuery对象的属性名相同，就可以接收到）
        userQuery.setToken(token);
        int save = userService.saveUser(userQuery);

        return save >= 1 ? R.OK() : R.FAIL();

    }

    /**
     * 编辑用户提交保存
     *
     * @param userQuery
     * @return
     */
    @PutMapping(value = "/api/user")
    public R updateUser(UserQuery userQuery, @RequestHeader(value = "Authorization") String token) {
        //前端是通过 FormData 提交过来的参数，怎么接收，方法如下：
        //1、request.getParamter("xxx");
        //2、@RequestParam(value="xxx")
        //3、UserQuery对象接收（只要表单input框里面的字段名和UserQuery对象的属性名相同，就可以接收到）
        userQuery.setToken(token);
        int update = userService.updateUser(userQuery);

        return update >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/api/user/{id}")
    public R delUser(@PathVariable(value = "id") Integer id) {
        int del = userService.delUserById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 根据id批量删除用户
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/api/user/batch")
    public R batchDelUser(@RequestParam(value = "ids") String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        int batchDel = userService.batchDelUserByIds(idList);
        return batchDel >= idList.size() ? R.OK() : R.FAIL();
    }

    /**
     * 查询所有负责人
     *
     * @return
     */
    @GetMapping(value = "/api/user/owner")
    public R owner() {
        List<TUser> tUserList = userService.getUserOwner();
        return R.OK(tUserList);
    }

    /**
     * 查询用户所有的菜单权限
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/api/user/{userId}/menu")
    public R loadPermissionMenuList(@PathVariable(value = "userId") Integer userId) {
        List<TPermission> tPermissionList = userService.getMenuByUserId(userId);
        return R.OK(tPermissionList);
    }
}
