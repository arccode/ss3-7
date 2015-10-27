package com.arccode.web.controller.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * SystemController :
 *
 * @author http://arccode.net
 * @since 2015-03-23 09:37
 */
@Controller
@RequestMapping("/")
public class SystemController {

    private final Logger logger = LoggerFactory.getLogger(SystemController.class);

    @RequestMapping("/dashboard")
    public String dashboard() {
        logger.info("进入控制面板.");
        return "dashboard";
    }

    @RequestMapping("/login-page")
    public ModelAndView loginPage() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @RequestMapping("/index")
    public String index() {

        return "index";
    }
}
