package com.tom.casclient.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(ModelMap modelMap, HttpSession session) {
        modelMap.addAttribute("msg", "welcome to spring-boot-cas-client!");
        modelMap.addAttribute("sessionId", session.getId());
        return "index";
    }

    @GetMapping(value = "/user")
    public String user(ModelMap modelMap) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelMap.addAttribute("user", user);
        return "user";
    }

    @GetMapping(value = "/main")
    public String main() {
        return "main";
    }

    @GetMapping(value = "/globleImages")
    public String globleImages() {
        return "globleImages";
    }

    @GetMapping(value = "/flood")
    public String flood() {
        return "flood";
    }

    @GetMapping(value = "/cutFill")
    public String cutFill() {
        return "cutFill";
    }

    @GetMapping(value = "/cutting")
    public String cutting() {
        return "cutting";
    }

    @GetMapping(value = "/showModel")
    public String showModel() {
        return "showModel";
    }

    @GetMapping(value = "/pipe")
    public String pipe() {
        return "pipe";
    }

    @GetMapping(value = "/attrs")
    public String attrs() {
        return "attrs";
    }
}
