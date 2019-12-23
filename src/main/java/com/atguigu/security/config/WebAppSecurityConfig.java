package com.atguigu.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author sauke
 */
@Configuration
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        //对请求进行授权
        security.authorizeRequests()
                //使用ANT风格设置要授权的URL地址
                .antMatchers("/layui/**", "/index.jsp")
                //允许上面使用ANT风格设置的全部请求
                .permitAll()
                //设置访问具体资源访问权限
                .antMatchers("/level1/**").hasAnyRole("学徒")
                .antMatchers("/level2/**").hasAnyRole("大师")
                .antMatchers("/level3/**").hasAnyRole("宗师")
                //其他未设置的全部请求
                .anyRequest()
                //需要认证
                .authenticated()
                .and()
                //指定使用表单进行登陆操作
                .formLogin()
                //登陆页面地址
                .loginPage("/index.jsp")
                //给登陆页面授权，任何请求都可以访问
                .permitAll()
                //具体指定登陆(检查用户名，密码是否正确)地址
                .loginProcessingUrl("/do/login")
                .permitAll()
                //指定用户名的请求参数名
                .usernameParameter("loginacct")
                //指定密码的请求参数名
                //credential表示密码的意思
                .passwordParameter("credential")
                //指定登陆成功以后前往的地址
                .defaultSuccessUrl("/main.html")
                .and()
                //开启用户退出功能
                .logout()
                //设置退出登陆的URL地址
                .logoutUrl("/my/app/logout")
                .logoutSuccessUrl("/index.jsp")
                .and()
                .exceptionHandling()
                //.accessDeniedPage("/to/no/auth/page.html")
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    request.setAttribute("message", accessDeniedException.getMessage());
                    request.getRequestDispatcher("/WEB-INF/views/no_auth.jsp").forward(request, response);
                })
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {

        builder
                //在内存中进行认证（通常不会用于实际开发）
                .inMemoryAuthentication()
                //指定用户名
                .withUser("韦小宝")
                //指定密码
                .password("123123")
                //指定当前账号具备的角色
                .roles("学徒")
                //指定当前账号具备的权限
                //.authorities("SAVE", "EAIT")
                .and()
                .withUser("郭靖")
                .password("456456")
                .roles("大师")
                //.authorities("SAVE", "EAIT")
                .and()
                .withUser("张三丰")
                .password("520520")
                .roles("宗师")
        ;
    }
}
