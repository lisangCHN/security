package com.lisang.security.controller;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Create by lisang on 2018/5/24.
 */
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    //定制请求的授权规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");
        //开启登陆功能
        http.formLogin().usernameParameter("user").passwordParameter("pwd")
                .loginPage("/userlogin");//如果没有权限就会跳转登录页面
        //定制规则后，那么loginPage的post请求就是登录


        //login请求登录页面
        //login?error表示登录失败
        http.logout().logoutSuccessUrl("/");//注销成功来到首页
        http.rememberMe().rememberMeParameter("remeber");//记住我功能
        //登录成功后将cookie发送给浏览器保存，通过检查免登陆
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        auth.inMemoryAuthentication().withUser("lisang")
                .password("123").roles("VIP1","VIP2")
                .and()
                .withUser("google").password("123").roles("VIP1","VIP3");

    }
}
