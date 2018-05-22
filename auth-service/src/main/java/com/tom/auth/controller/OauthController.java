package com.tom.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Api(value = "授权服务",description="单点登录授权服务")
@RestController
@RequestMapping("/oauth")
public class OauthController {
    @ApiOperation(value = "获取用户",notes="oauth2调用用户信息")
    @GetMapping("/principal")
    public Principal user(Principal principal) {
        return principal;
    }
    @ApiOperation(value = "测试",notes="简单的测试")
    @GetMapping
    public String hello() {
        return "Hello World";
    }
}
