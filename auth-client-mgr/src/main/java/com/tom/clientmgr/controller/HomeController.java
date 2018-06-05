package com.tom.clientmgr.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

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
    public String getPage(Model model) {
        return "forward:/index";
    }

    @RequestMapping("/index")
    public String getIndexPage(Model model) {
        model.addAttribute("authClientMgrHostname",auth_client_mgr_hostname);
        model.addAttribute("authClientMgrPort",auth_client_mgr_port);
        model.addAttribute("authClientMgrContextPath",auth_client_mgr_contextPath);
        model.addAttribute("authServiceHostname",auth_service_hostname);
        model.addAttribute("authServicePort",auth_service_port);
        model.addAttribute("authServiceContextPath",auth_service_contextPath);
        return "index";
    }

    @RequestMapping("/secure")
    public String getSecurePage() {
        return "secure";
    }

    @RequestMapping("/pages/{pagename}")
    public String redirectPage(@PathVariable String pagename, Model model){
        model.addAttribute("authClientMgrHostname",auth_client_mgr_hostname);
        model.addAttribute("authClientMgrPort",auth_client_mgr_port);
        model.addAttribute("authClientMgrContextPath",auth_client_mgr_contextPath);
        model.addAttribute("authServiceHostname",auth_service_hostname);
        model.addAttribute("authServicePort",auth_service_port);
        model.addAttribute("authServiceContextPath",auth_service_contextPath);
        return pagename;
    }
}
