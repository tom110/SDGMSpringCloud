package com.tom.casclient.config.oauth2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="token")
public class TokenPicker {

    private String v;

    public String v() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
