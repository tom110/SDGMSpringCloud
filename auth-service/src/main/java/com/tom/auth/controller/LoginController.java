package com.tom.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(value = "登录页面服务",description="单点登录页面服务")
@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/")
    public String getHomePage() {
        LOGGER.debug("Getting / page");
        return "login";
    }
    @RequestMapping("login")
    public String login() {
        LOGGER.debug("Getting login page");
        return "login";
    }

    @RequestMapping("403")
    public String p403(){
        LOGGER.debug("403");
        return "403";
    }

}
