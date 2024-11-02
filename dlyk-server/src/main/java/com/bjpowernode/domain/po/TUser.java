package com.bjpowernode.domain.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户表
 * t_user
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TUser implements UserDetails, Serializable {
    /**
     * 主键，自动增长，用户ID
     */
    private Integer id;

    /**
     * 登录账号
     */
    private String loginAct;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户手机
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 账户是否没有过期，0已过期 1正常
     */
    private Integer accountNoExpired;

    /**
     * 密码是否没有过期，0已过期 1正常
     */
    private Integer credentialsNoExpired;

    /**
     * 账户是否没有锁定，0已锁定 1正常
     */
    private Integer accountNoLocked;

    /**
     * 是否启动账户，0禁用 1启用
     */
    private Integer accountEnabled;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 编辑时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;

    /**
     * 编辑人
     */
    private Integer editBy;

    /**
     * 最近登录时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**
     * 用户的角色列表
     */
    private List<String> stringRoleList;

    /**
     * 用户的权限列表
     */
    private List<String> stringAuthorityList;

    /**
     * 一对一映射关联
     */
    private TUser createByPO;
    private TUser editByPO;

    private static final long serialVersionUID = 1L;

    //--------------实现UserDetails这个接口的7个抽象方法----------------

    /**
     * 获取登录账号
     *
     * @return
     */
    @JsonIgnore
    @Override
    public String getUsername() {
        return this.loginAct;
    }

    /**
     * 获取登录密码
     *
     * @return
     */
    @JsonIgnore
    @Override
    public String getPassword() {
        return this.loginPwd;
    }

    /**
     * 返回用户的权限list信息
     *
     * @return
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
        stringAuthorityList.forEach(auth -> {
            grantedAuthoritiesList.add(new SimpleGrantedAuthority(auth));
        });
        return grantedAuthoritiesList;
    }

    /**
     * 是不是 登录账号还没有过期？ true表示账号还没有过期，false表示账号已经过期了
     *
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNoExpired == 1;
    }

    /**
     * 是不是 登录账号还没有锁定？ true表示账号还没有锁定，false表示账号已经锁定了
     *
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNoLocked == 1;
    }

    /**
     * 是不是 登录密码还没有过期？ true表示密码还没有过期，false表示密码已经过期了
     *
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNoExpired == 1;
    }

    /**
     * 是不是 账号是可用的？ true表示账号可用，false表示账号已经被禁用
     *
     * @return
     */
    @JsonIgnore //@JsonIgnore注解作⽤：在json序列化时将pojo中的⼀些属性忽略掉，标记在属性或者⽅法上，返回的json数据即不包含该属性
    @Override
    public boolean isEnabled() {
        return this.accountEnabled == 1;
    }
}