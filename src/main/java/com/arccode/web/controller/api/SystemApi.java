package com.arccode.web.controller.api;

import com.alibaba.fastjson.JSON;
import com.arccode.web.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;

/**
 * SystemApi : 系统api接口, 该类定义的接口只返回JSON
 *
 * @author http://arccode.net
 * @since 2015-03-23 16:59
 */
@Controller
@RequestMapping("/")
public class SystemApi {

    private final Logger logger = LoggerFactory.getLogger(SystemApi.class);

    @RequestMapping(value = "/u/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User user(@PathVariable("id") Long userId) {
        logger.info("查询用户, id = " + userId);
        User user = new User("zhangan", "pwd", new HashSet<GrantedAuthority>());

        logger.info("userInfo = " + JSON.toJSONString(user));

        return user;
    }
}
