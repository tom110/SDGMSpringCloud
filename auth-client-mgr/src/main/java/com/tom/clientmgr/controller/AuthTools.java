package com.tom.clientmgr.controller;

import com.tom.clientmgr.services.OauthClientDetailsService;
import com.tom.clientmgr.services.UsersService;
import com.tom.clientmgr.util.MD5Util;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.repository.query.Param;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
    @Value("${auth-service.hostname}")
    private String auth_service_hostname;
    @Value("${auth-service.port}")
    private String auth_service_port;
    @Value("${auth-service.contextPath}")
    private String auth_service_contextPath;
    @Value("${auth-client-server.hostname}")
    private String auth_client_server_hostname;
    @Value("${auth-client-server.port}")
    private String auth_client_server_port;
    @Value("${auth-client-server.contextPath}")
    private String auth_client_server_contextPath;

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    private UsersService usersService;

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
    public void refresh(@RequestParam("host") String host, Principal principal) throws IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        String username=((OAuth2Authentication) principal).getPrincipal().toString();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, usersService.getUsersByUsername(username).getPassword());
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

        HttpPost httpMethod = new HttpPost("http://"+host+"/mgmt/bus/refresh");
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

    @RequestMapping(value = "/getClientToken", method = RequestMethod.GET)
    public String getClientToken(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("grant_type") String grant_type,
                                 @RequestParam("scope") String scope) throws Exception {
        CredentialsProvider provider = new BasicCredentialsProvider();
//        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("client",
//                oauthClientDetailsService.getById("client").getClient_secret());
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("client",
                "secret");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

        HttpPost httpMethod1 = new HttpPost("http://"+auth_client_server_hostname+":"+auth_client_server_port+"/"
                +auth_client_server_contextPath+"/oauth/token");
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("username", username));
        list.add(new BasicNameValuePair("password", password));
        list.add(new BasicNameValuePair("grant_type", grant_type));
        list.add(new BasicNameValuePair("scope", scope));

        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
        httpMethod1.setEntity(uefEntity);
        HttpResponse response1 = httpClient.execute(httpMethod1);
        return EntityUtils.toString(response1.getEntity());
    }

    @RequestMapping(value = "/getRefreshToken", method = RequestMethod.GET)
    public String getClientToken(@RequestParam("refreshToken") String refreshToken) throws Exception {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("client",
                "secret");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

        HttpPost httpMethod1 = new HttpPost("http://"+auth_client_server_hostname+":"+auth_client_server_port+"/"
                +auth_client_server_contextPath+"/oauth/token");

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("grant_type", "refresh_token"));
        list.add(new BasicNameValuePair("refresh_token", refreshToken));

        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
        httpMethod1.setEntity(uefEntity);
        HttpResponse response1 = httpClient.execute(httpMethod1);
        return EntityUtils.toString(response1.getEntity());
    }


}
