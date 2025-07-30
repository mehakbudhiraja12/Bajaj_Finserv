package com.example.bajajjavaapp.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class WebhookService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void executeFlow() {
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        Map<String, String> body = new HashMap<>();
        body.put("name", "Mehak Budhiraja");
        body.put("regNo", "REG2210991917");
        body.put("email", "mehak1917.be22@chitkara.edu.in");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String webhook = (String) response.getBody().get("webhook");
            String accessToken = (String) response.getBody().get("accessToken");

            String sqlQuery = solveSqlQuestion1();
            sendFinalQuery(webhook, accessToken, sqlQuery);
        } else {
            System.out.println("Webhook generation failed.");
        }
    }

    private String solveSqlQuestion1() {
        return """
            SELECT e.name, d.name AS department_name
            FROM employee e
            JOIN department d ON e.dept_id = d.id
            WHERE e.salary = (SELECT MAX(salary) FROM employee);
        """;
    }

    private void sendFinalQuery(String webhook, String accessToken, String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, String> body = new HashMap<>();
        body.put("finalQuery", query);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(webhook, request, String.class);

        System.out.println("Submission Response: " + response.getBody());
    }
}

