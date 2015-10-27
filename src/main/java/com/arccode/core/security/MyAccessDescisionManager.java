package com.arccode.core.security;

import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * MyAccessDescisionManager:  访问决策器，决定某个用户具有的角色，是否有足够的权限去访问某个资源 ;做最终的访问控制决定
 *
 * @author http://arccode.net
 * @since 2015-03-20 17:35
 */
//@Component("myAccessDescisionManager")
public class MyAccessDescisionManager implements AccessDecisionManager {

    private Logger logger = LoggerFactory.getLogger(MyAccessDescisionManager.class);

    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        // TODO Auto-generated method stub
        logger.info("MyAccessDescisionManager.decide()------------------验证用户是否具有一定的权限--------");
        if (configAttributes == null) return;
        Iterator<ConfigAttribute> it = configAttributes.iterator();
        while (it.hasNext()) {
            String needRole = it.next().getAttribute();
            //authentication.getAuthorities()  用户所有的权限

            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (needRole.equals(ga.getAuthority())) {
                    logger.info("MyAccessDescisionManager.decide()------------------获得授权--------");
                    return;
                }
            }
        }
        logger.info("MyAccessDescisionManager.decide()------------------未授权--------");
        throw new AccessDeniedException("--------MyAccessDescisionManager：decide-------权限认证失败！");

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return true;
    }

}
