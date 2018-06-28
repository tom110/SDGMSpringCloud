package com.tom.casclient.config.oauth2;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class MyRequestInterceptor implements RequestInterceptor {

    @Value("${security.token}")
    private String token;

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization",token);
        template.header("Content-Type","application/x-www-form-urlencoded");
    }
}
