package com.example.otrtesttask.rest;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class RestApiTest {
    @Test
    public void employeeDoesNotExists() throws IOException {
        Integer id = -1;
        HttpUriRequest request = new HttpGet(String.format("http://localhost:8080/employees/%d", id));
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        MatcherAssert.assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
    }

    @Transactional
    @Test
    public void insertNewEmployee() throws IOException {
        String json = "{\"managerId\": 1, \"positionId\":\"2\", \"fullName\":\"3\",\"branchId\":\"4\"}";
        HttpPost httpPost = new HttpPost("http://localhost:8080/employees/");
        httpPost.setEntity(new StringEntity(json));
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpPost);
        MatcherAssert.assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));
    }
}
