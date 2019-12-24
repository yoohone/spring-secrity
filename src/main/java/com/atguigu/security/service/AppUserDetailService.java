package com.atguigu.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author sauke
 */
@Service
public class AppUserDetailService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //1.使用SQL语句根据用户名查询用户对象
        String sql = "SELECT `id`,`loginacct`,`userpswd`,`username`,`email`,`createtime` FROM t_admin WHERE `loginacct`= ?";
        System.out.println(sql);
        //2.获取查询结果
        Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql, username);
        //3.获取用户名、密码数据
        String loginacct = resultMap.get("loginacct").toString();
        String userpswd = resultMap.get("userpswd").toString();

        //4.创建权限列表
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("ROLE_学徒", "USER");
        //5.创建用户对象
        User user = new User(loginacct, userpswd, authorityList);

        return user;
    }
}
