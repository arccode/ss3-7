package com.arccode.core.security;

import com.arccode.web.dao.RoleMapper;
import com.arccode.web.dao.UserMapper;
import com.arccode.web.model.Resource;
import com.arccode.web.model.Role;
import com.arccode.web.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * MyUserDetailsServiceImpl : 加载用户authentication信息
 *
 * @author http://arccode.net
 * @since 2015-03-22 22:50
 */
@Component("myUserDetailsService")
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(MyUserDetailsServiceImpl.class);

    @javax.annotation.Resource
    private UserMapper userMapper;

    @javax.annotation.Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("username is " + username);

        User user = userMapper.selectByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(user);
        logger.info(user.getUsername());
        return new User(user.getUsername(),user.getPassword(),grantedAuths);

    }

    //取得用户的权限
    private Set<GrantedAuthority> obtionGrantedAuthorities(User user) {
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        List<Role> roles = user.getRoles();

        for(Role role : roles) {
            authSet.add(new SimpleGrantedAuthority(role.getRoleName()));
            Role innerRole = roleMapper.selectByRoleName(role.getRoleName());
            List<Resource> tempRes = innerRole.getResources();
            for(Resource res : tempRes) {
                authSet.add(new SimpleGrantedAuthority(res.getResourceName()));
            }
        }
        return authSet;
    }



}
