package com.tom.clientmgr.controller;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RefreshScope
public class AuthTools {

    @Value("${config-center.user.name}")
    private String user;
    @Value("${config-center.user.password}")
    private String password;
    @Value("${config-center.hostname}")
    private String config_center_hostname;
    @Value("${config-center.port}")
    private String config_center_port;
    @Value("${config-center.test}")
    private String config_center_test;
    @Value("${gateway.hostname}")
    private String gateway_hostname;
    @Value("${gateway.port}")
    private String gateway_port;

    private Logger log = LogManager.getLogger(AuthTools.class);

    @RequestMapping(value = "/encrypt", method = RequestMethod.POST)
    public String encrypt(@Param("encrypt") String encrypt) throws IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, password);
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

        HttpPost httpMethod = new HttpPost("http://"+config_center_hostname+":"+config_center_port+"/encrypt");
        HttpEntity entity = new StringEntity(encrypt, Consts.UTF_8);
        httpMethod.setEntity(entity);
        HttpResponse response = httpClient.execute(httpMethod);
        return EntityUtils.toString(response.getEntity());
    }


    @RequestMapping(value = "/decrypt", method = RequestMethod.POST)
    public String decryption(@Param("decrypt") String decrypt) throws IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, password);
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

        HttpPost httpMethod = new HttpPost("http://"+config_center_hostname+":"+config_center_port+"/decrypt");
        HttpEntity entity = new StringEntity(decrypt, Consts.UTF_8);
        httpMethod.setEntity(entity);
        HttpResponse response = httpClient.execute(httpMethod);
        return EntityUtils.toString(response.getEntity());
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public void refresh() throws IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, password);
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

        HttpPost httpMethod = new HttpPost("http://"+config_center_hostname+":"+config_center_port+"/bus/refresh");
        HttpResponse response = httpClient.execute(httpMethod);
        log.debug(EntityUtils.toString(response.getEntity()));

    }

    @RequestMapping(value = "/getYml", method = RequestMethod.GET)
    public String getYml(@Param("projectName") String projectName) throws IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, password);
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

        HttpGet httpMethod1 = new HttpGet("http://"+config_center_hostname+":"+config_center_port+"/"+projectName+".yml");
        HttpResponse response1 = httpClient.execute(httpMethod1);
        return EntityUtils.toString(response1.getEntity());
    }

    @RequestMapping(value = "/getRoutes", method = RequestMethod.GET)
    public String getRoutes() throws IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, password);
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

        HttpGet httpMethod1 = new HttpGet("http://"+gateway_hostname+":"+gateway_port+"/routes");
        HttpResponse response1 = httpClient.execute(httpMethod1);
        return EntityUtils.toString(response1.getEntity());
    }
}
