package com.cpcompass.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CodeforcesClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean handleExists(String handle) {

        String url =
                "https://codeforces.com/api/user.info?handles=" + handle;

        try {

            restTemplate.getForObject(url, String.class);

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}