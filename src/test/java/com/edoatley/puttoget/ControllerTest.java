package com.edoatley.puttoget;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void messageInLength() throws Exception {
        String url = String.format("http://localhost:%s/hello?name=short", port);
        assertThat(this.restTemplate.getForObject(url, String.class)).contains( "Hello from GET short");
    }
    
    @Test
    public void messageNotInLength() throws Exception {
        String url = String.format("http://localhost:%s/hello?name=veryveryveryverylong", port);
        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void goodPut() {
        String url = String.format("http://localhost:%s/hello", port);
        HttpEntity<RequestBody> entity = new HttpEntity<>(new RequestBody("short"));
        ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        assertThat(response.getBody()).isEqualTo("Hello from GET short");
    }

    @Test
    public void badPut() {
        String url = String.format("http://localhost:%s/hello", port);
        HttpEntity<RequestBody> entity = new HttpEntity<>(new RequestBody("veryveryveryverylong"));
        ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}