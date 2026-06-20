package com.cpcompass.client;

import com.cpcompass.dto.sync.CfContestResponse;
import com.cpcompass.dto.sync.CfSubmissionResponse;
import com.cpcompass.dto.sync.CfUserInfoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CodeforcesClient {

    private static final String BASE_URL =
            "https://codeforces.com/api";

    private final RestTemplate restTemplate =
            new RestTemplate();

    public boolean handleExists(String handle) {

        String url =
                BASE_URL + "/user.info?handles=" + handle;

        try {

            restTemplate.getForObject(
                    url,
                    String.class
            );

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    public CfUserInfoResponse getUserInfo(String handle) {

        String url =
                BASE_URL + "/user.info?handles=" + handle;

        return restTemplate.getForObject(
                url,
                CfUserInfoResponse.class
        );
    }

    public CfContestResponse getContestHistory(String handle) {

        String url =
                BASE_URL + "/user.rating?handle=" + handle;

        return restTemplate.getForObject(
                url,
                CfContestResponse.class
        );
    }

    public CfSubmissionResponse getSubmissions(String handle) {

        String url =
                BASE_URL + "/user.status?handle=" + handle;

        return restTemplate.getForObject(
                url,
                CfSubmissionResponse.class
        );
    }
}