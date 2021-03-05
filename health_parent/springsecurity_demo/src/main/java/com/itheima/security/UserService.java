package com.itheima.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实现认证与授权的地方
 * </p>
 *
 * @author: Eric
 * @since: 2021/2/23
 */
public class UserService implements UserDetailsService {

    //模拟数据库中的用户数据， key=用户名 value=User（health）对象
    public static Map<String, com.itheima.health.pojo.User> map = new HashMap<String, com.itheima.health.pojo.User>();

    static {
        com.itheima.health.pojo.User user1 = new com.itheima.health.pojo.User();
        user1.setUsername("admin");
        user1.setPassword("$2a$10$tTtejrAh99wL13tlKNlAZO.6OWLGnbRb8BJqWX.GpxtVgoo6lwoUO");

        com.itheima.health.pojo.User user2 = new com.itheima.health.pojo.User();
        user2.setUsername("zhangsan");
        user2.setPassword("$2a$10$tTtejrAh99wL13tlKNlAZO.6OWLGnbRb8BJqWX.GpxtVgoo6lwoUO");

        map.put(user1.getUsername(), user1);
        map.put(user2.getUsername(), user2);
    }

    /**
     *
     * @param username
     * @return UserDetails 用户的信息 <security:user name="" password="" authorities=""/>
     *  用户名，密码(数据库的密码)，用户拥有的权限集合
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库查询,根据用户名查询用户信息, 数据库的用户信息
        com.itheima.health.pojo.User userInDb = map.get(username);
        // 用户名存在
        if(null != userInDb) {
            //String username,
            // String password,
            // Collection<? extends GrantedAuthority> authorities 权限集合
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            // 构建spring security用户信息对象, 代替：<security:user name="" password="" authorities=""/>
            // 密码是密文，且security.xml配置了密码加密器，密码不需要加前缀{}。
            // 授权
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
            authorities.add(authority);

            authorities.add(new SimpleGrantedAuthority("add")); // 添加
            authorities.add(new SimpleGrantedAuthority("delete")); // 删除
            // 授予了这个用户3个权限
            User securityUser = new User(username, userInDb.getPassword(), authorities);
            // 返回给security的认证管理器 authentication-provider
            return securityUser;
        }
        return null;
    }
}
