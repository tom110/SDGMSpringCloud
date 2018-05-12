package com.tom.config;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigApplicationTest {
    @Test
    public void contextLoads() throws IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("user", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

        HttpPost httpMethod = new HttpPost("http://localhost:8888/encrypt");
        HttpEntity entity = new StringEntity("root", Consts.UTF_8);
        httpMethod.setEntity(entity);
        HttpResponse response = httpClient.execute(httpMethod);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void contextLoads2() throws IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("user", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

        HttpPost httpMethod = new HttpPost("http://localhost:8888/decrypt");
        HttpEntity entity = new StringEntity("AQB28HymFp2NYxLo1RV55Ze/2tBlTELzn6ojakPUbkupLMeqqxg0yyxFtYHUo6WTKGoAxUATyrRoV+RRvayJyY87Y4NykQLvHNWsN/Tj77PfbWl6L1hn3KrOMDilD5zqibNYC4EOTOnyCSqhKg9JbzfWJ8Kq/SYomRKHUmIbKVKTMVe95s0gBMPJPiv8DdwzeZtwWKVDIRVEoSIDJdEJ+3avWS2RrTZgVIG56JH3bCQxZywJQAoTV7CK5Fz0KlfYUzXadWnug1iE7O8p+qbiu8nxfx1XyhwAY19Sby6ETdlBBKion4D0xy829Sp3byEJ7GG3yKEYaNapV7xRnJGyG8uecPGr70yCbUGiHhe73qEdhMiYoP6zXhBUJaahQ3jpmMI=", Consts.UTF_8);
        httpMethod.setEntity(entity);
        HttpResponse response = httpClient.execute(httpMethod);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
    @Test
    public void contextLoads3() throws IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("user", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

        HttpPost httpMethod = new HttpPost("http://localhost:8888/bus/refresh");
        HttpResponse response = httpClient.execute(httpMethod);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}
