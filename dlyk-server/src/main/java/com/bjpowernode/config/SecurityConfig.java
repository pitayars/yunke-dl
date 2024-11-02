package com.bjpowernode.config;

import com.bjpowernode.config.filter.JwtFilter;
import com.bjpowernode.config.handler.MyAccessDeniedHandler;
import com.bjpowernode.config.handler.MyAuthenticationFailureHandler;
import com.bjpowernode.config.handler.MyAuthenticationSuccessHandler;
import com.bjpowernode.config.handler.MyLogoutSuccessHandler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableMethodSecurity(prePostEnabled = true)
@Configuration //这是配置类，相当于原来spring的xml配置
public class SecurityConfig {

    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Resource
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Resource
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置spring security跨域支持的Bean
     *
     * @return
     */
    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * 配置spring security的行为
     *
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfigurationSource configurationSource) throws Exception {
        return httpSecurity
                .formLogin((formLogin) -> {
                    formLogin.usernameParameter("loginAct") //账号
                            .passwordParameter("loginPwd") //密码
                            .loginProcessingUrl("/api/login") //登录地址
                            .successHandler(myAuthenticationSuccessHandler) //登录成功，就执行该handler，在handler中给前端返回json
                            .failureHandler(myAuthenticationFailureHandler); //登录失败，就执行该handler，在handler中给前端返回json
                })

                .sessionManagement( (session) -> { //spring security默认使用session记住当前登录人的信息
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); //不使用session，相当于把session禁用了
                })

                .logout((logout) -> {
                    logout.logoutUrl("/api/logout") //退出登录的接口地址
                            .logoutSuccessHandler(myLogoutSuccessHandler); //退出成功，就执行该handler，在handler中给前端返回json
                })

                .authorizeHttpRequests( (authorize) -> {
                    authorize.anyRequest().authenticated(); //除了上面的请求，其他请求都需要登录后才能访问
                })

                .exceptionHandling( (exception) -> {
                    //访问拒绝（没有权限），就执行该handler，在handler中给前端返回json
                    exception.accessDeniedHandler(myAccessDeniedHandler);
                })

                .csrf((csrf) -> {
                    csrf.disable(); //禁用跨站请求伪造，因为前后端分离的请求，没有传_csrf这个随机字符串
                })

                .cors((cors) -> { //解决spring security跨域问题
                    cors.configurationSource(configurationSource);
                })

                //在退出登录的Filter前加入我们的jwt验证Filter
                .addFilterBefore(jwtFilter, LogoutFilter.class)

                .build();
    }
}
