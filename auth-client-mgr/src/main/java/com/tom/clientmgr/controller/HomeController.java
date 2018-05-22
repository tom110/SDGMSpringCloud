package com.tom.clientmgr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String getPage() {
        return "forward:/index";
    }

    @RequestMapping("/index")
    public String getIndexPage() {
        return "index";
    }

    @RequestMapping("/secure")
    public String getSecurePage() {
        return "secure";
    }

    @RequestMapping("/pages/{pagename}")
    public String redirectPage(@PathVariable String pagename){
        return pagename;
    }
}
