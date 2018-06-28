package com.tom.casclient.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "auth-rest-service", fallback = AuthRestService.ServiceBClientFallback.class)
public interface AuthRestService {

    @GetMapping(value = "/rest/serviceb")
    String printServiceB();

    @PostMapping(value="/rest/getAuthorities")
    List<String> getAuthorities(@RequestParam("username") String username);

    @Component
    class ServiceBClientFallback implements AuthRestService {

        private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBClientFallback.class);

        @Override
        public String printServiceB() {
            LOGGER.info("异常发生，进入fallback方法");
            return "auth-client-mgr FAILED! - FALLING BACK";
        }

        @Override
        public List<String> getAuthorities(String username) {
            LOGGER.info("用户没有任何权限，进入fallback方法");
            return null;
        }
    }
}
