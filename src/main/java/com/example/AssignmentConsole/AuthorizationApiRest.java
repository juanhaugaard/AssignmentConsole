package com.example.AssignmentConsole;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Profile({"!stubs"})
public class AuthorizationApiRest implements AuthorizationAPI {
    @Value("${authorization-domain}")
    private String domainAuthorization;
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

    private String restGet(String url)throws IOException {
        Request request = new Request.Builder().get()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String ret=response.body().string();
        return ret;
    }

    @Override
    public String getUrl_subjects() throws IOException {
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
}
