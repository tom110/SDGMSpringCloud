package com.tom.casclient.config.oauth2;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
public class MyRequestInterceptor implements RequestInterceptor {

    @Autowired
    private TokenPicker tokenPicker;

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization",tokenPicker.v());
        template.header("Content-Type","application/x-www-form-urlencoded");
    }
}
