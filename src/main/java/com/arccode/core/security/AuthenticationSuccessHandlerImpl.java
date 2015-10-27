package com.arccode.core.security;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthenticationSuccessHandlerImpl : 认证成功回调该类的onAuthenticationSuccess方法
 *
 * @author http://arccode.net
 * @since 2015-03-15 00:33
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(AuthenticationFailureHandlerImpl.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 记录用户登陆日志
        String ip = getIpAddress(request);
        // 获取登陆用户信息, e.g:
        //{"accountNonExpired":true,"accountNonLocked":true,"authorities":[{"authority":"ROLE_ADMIN"},{"authority":"ROLE_USER"}],"credentialsNonExpired":true,"enabled":true,"username":"admin"}
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        logger.info("用户[" + user.getUsername() + "]的登陆地址为[" + ip +"], 角色为" + user.getAuthorities());
        response.sendRedirect(request.getContextPath() + "/api/index");

    }

    protected String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
