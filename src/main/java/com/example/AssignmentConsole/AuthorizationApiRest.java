package com.example.AssignmentConsole;


import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@Profile({"!stubs"})
public class AuthorizationApiRest implements AuthorizationAPI {
    @Value("${authorization-domain}")
    private String domainAuthorization;
    @Value("${token-prefix}")
    private String tokenPrefix;
    @Value("${token-value}")
    private String tokenValue;

    public final String url_subjects = "/api/subjects";
    public final String url_scopes = "/api/scopes";
    public final String url_privileges = "/api/privileges";
    public final String url_assignments = "/api/assignments";

    private String subjects;
    private String privileges;
    private String scopes;
    private String assignments;

    private final OkHttpClient client = new OkHttpClient();
    public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public final String TOKEN = String.format("%s %s", tokenPrefix, tokenValue);

    public AuthorizationApiRest() {
        log.info("constructing {}", this.getClass().getSimpleName());
    }

    private String restGet(final String url)throws IOException {
        log.debug("executing GET {}" , url);
        Request request = new Request.Builder()
                .addHeader("Authorization", TOKEN)
                .addHeader("Accept", JSON.toString())
                .get()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private String restPut(final String url, final String jsonBody)throws IOException {
        log.debug("executing PUT {}: {}" , url, jsonBody);
        RequestBody requestBody = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .addHeader("Authorization", TOKEN)
                .addHeader("Accept", JSON.toString())
                .put(requestBody)
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    public String getSubjects() throws IOException {
        if (subjects == null)
            subjects = restGet(domainAuthorization + url_subjects);
        return subjects;
    }

    @Override
    public String getScopes() throws IOException {
        if (scopes == null)
            scopes = restGet(domainAuthorization + url_scopes);
        return scopes;
    }

    @Override
    public String getPrivileges() throws IOException {
        if (privileges == null)
            privileges = restGet(domainAuthorization + url_privileges);
        return privileges;
    }

    @Override
    public String getAssignments() throws IOException {
        if (assignments == null)
            assignments = restGet(domainAuthorization + url_assignments);
        return assignments;
    }

    @Override
    public String putAssignment(final String jsonBody) throws IOException {
        return restPut(domainAuthorization + url_assignments, jsonBody);
    }
}
