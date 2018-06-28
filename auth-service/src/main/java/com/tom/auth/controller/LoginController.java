package com.tom.auth.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "登录页面服务", description = "单点登录页面服务")
@Controller
@RefreshScope
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    @Value("${auth-client-mgr.hostname}")
    private String auth_client_mgr_hostname;
    @Value("${auth-client-mgr.port}")
    private String auth_client_mgr_port;
    @Value("${auth-client-mgr.contextPath}")
    private String auth_client_mgr_contextPath;
    @Value("${auth-service.hostname}")
    private String auth_service_hostname;
    @Value("${auth-service.port}")
    private String auth_service_port;
    @Value("${auth-service.contextPath}")
    private String auth_service_contextPath;

    @RequestMapping("/")
    public String getHomePage(Model model) {
        LOGGER.debug("Getting / page");
        model.addAttribute("authClientMgrHostname", auth_client_mgr_hostname);
        model.addAttribute("authClientMgrPort", auth_client_mgr_port);
        model.addAttribute("authClientMgrContextPath", auth_client_mgr_contextPath);
        model.addAttribute("authServiceHostname", auth_service_hostname);
        model.addAttribute("authServicePort", auth_service_port);
        model.addAttribute("authServiceContextPath", auth_service_contextPath);
        return "exit";
    }

    @RequestMapping("login")
    public String login() {
        LOGGER.debug("Getting login page");
        return "login";
    }

    @RequestMapping("403")
    public String p403() {
        LOGGER.debug("403");
        return "403";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "login";
    }

    @RequestMapping("/oauth/exit")
    public String exit(HttpServletRequest request, HttpServletResponse response, Model model) {
        // token can be revoked here if needed
        new SecurityContextLogoutHandler().logout(request, null, null);
//        try {
        //sending back to client app
//            response.sendRedirect(request.getHeader("referer"));
        model.addAttribute("authClientMgrHostname", auth_client_mgr_hostname);
        model.addAttribute("authClientMgrPort", auth_client_mgr_port);
        model.addAttribute("authClientMgrContextPath", auth_client_mgr_contextPath);
        model.addAttribute("authServiceHostname", auth_service_hostname);
        model.addAttribute("authServicePort", auth_service_port);
        model.addAttribute("authServiceContextPath", auth_service_contextPath);
        return "exit";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "login";
//        }
    }

}
