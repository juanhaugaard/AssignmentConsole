package com.example.AssignmentConsole;


import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Component
@Profile({"!stubs"})
public class AuthorizationApiRest implements AuthorizationAPI {
    public final String url_subjects = "/api/subjects";
    public final String url_scopes = "/api/scopes";
    public final String url_privileges = "/api/privileges";
    public final String url_assignments = "/api/assignments";
    public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    @Value("${authorization-domain}")
    private String domainAuthorization;
    @Value("${token-prefix}")
    private String tokenPrefix;
    @Value("${token-value}")
    private String tokenValue;
    private String subjects;
    private String privileges;
    private String scopes;
    private String assignments;
    private String TOKEN;

    public AuthorizationApiRest() {
        log.info("constructing {}", this.getClass().getSimpleName());
    }

    @PostConstruct
    public void init() {
        TOKEN = String.format("%s %s", tokenPrefix, tokenValue);
    }

    @Override
    public String getSubjects() {
        if (subjects == null)
            try {
                subjects = restGet(domainAuthorization + url_subjects);
            } catch (IOException e) {
                return e.getMessage();
            }
        return subjects;
    }

    @Override
    public String getScopes() {
        if (scopes == null)
            try {
                scopes = restGet(domainAuthorization + url_scopes);
            } catch (IOException e) {
                return e.getMessage();
            }
        return scopes;
    }

    @Override
    public String getPrivileges() {
        if (privileges == null)
            try {
                privileges = restGet(domainAuthorization + url_privileges);
            } catch (IOException e) {
                return e.getMessage();
            }
        return privileges;
    }

    @Override
    public String getAssignments() {
        if (assignments == null)
            try {
                assignments = restGet(domainAuthorization + url_assignments);
            } catch (IOException e) {
                return e.getMessage();
            }
        return assignments;
    }

    @Override
    public String putAssignment(final String jsonBody) {
        try {
            return restPut(domainAuthorization + url_assignments, jsonBody);
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private String restGet(final String url) throws IOException {
        Request request = new Request.Builder()
                .addHeader("Authorization", TOKEN)
                .addHeader("Accept", JSON.toString())
                .get()
                .url(url)
                .build();
        log.debug("executing {}, headers: {}", request, request.headers());
        IOException internal = null;
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String body = response.body().string();
                log.debug("result code: {}, headers: {}, body: {}", response.code(), response.headers(), body);
                return body;
            } else {
                String body = String.format("{\"code\": %d, \"message\": \"%s, %s\"}",
                        response.code(), response.message(), url);
                internal = new IOException(body);
            }
        } catch (IOException e) {
            String msg = String.format("{\"code\": %d, \"message\": \"%s\"}", 500, e.getMessage());
            throw new IOException(msg);
        }
        throw internal;
    }

    private String restPut(final String url, final String jsonBody) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .addHeader("Authorization", TOKEN)
                .addHeader("Accept", JSON.toString())
                .put(requestBody)
                .url(url)
                .build();
        log.debug("executing {}, headers: {}", request, request.headers());
        IOException internal = null;
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String body = response.body().string();
                log.debug("result code: {}, headers: {}, body: {}", response.code(), response.headers(), body);
                return body;
            } else {
                String body = String.format("{\"code\": %d, \"message\": \"%s, %s\"}",
                        response.code(), response.message(), url);
                internal = new IOException(body);
            }
        } catch (IOException e) {
            String msg = String.format("{\"code\": %d, \"message\": \"%s\"}", 500, e.getMessage());
            throw new IOException(msg);
        }
        throw internal;
    }
}
