package com.tom.authrest.controller;

import com.tom.authrest.domian.Authority;
import com.tom.authrest.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RefreshScope
@RestController
public class OauthServiceController {
    @Autowired
    EurekaDiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String msg;

    @Autowired
    private AuthorityService authorityService;

    @GetMapping(value = "/serviceb")
    public String printServiceB() {
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        return serviceInstance.getServiceId() + " (" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + ")" + "===>Say " + msg;
    }

    @PostMapping(value="/getAuthorities")
    public List<String> getAuthorities(@RequestParam("username") String username){
        List<Authority> authorities= authorityService.getAuthoritiesByUsername(username);
        return  authorities.stream().map(authority -> {
            return authority.getAuthority().toString();
        }).collect(Collectors.toList());
    }
}
