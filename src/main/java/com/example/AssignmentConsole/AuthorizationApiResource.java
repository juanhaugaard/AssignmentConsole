package com.example.AssignmentConsole;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import spark.utils.IOUtils;
import spark.utils.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Slf4j
@Component
@Profile({"stubs"})
public class AuthorizationApiResource implements AuthorizationAPI {
    public final String url_subjects = "classpath:subjects.json";
    public final String url_scopes = "classpath:scopes.json";
    public final String url_privileges = "classpath:privileges.json";
    public final String url_assignments = "classpath:assignments.json";
    private String subjects;
    private String privileges;
    private String scopes;
    private String assignments;

    public AuthorizationApiResource() {
        log.info("constructing {}", this.getClass().getSimpleName());
    }

    private String jsonLoader(final String resource) throws IOException {
        log.debug("executing GET {}" , resource);
        URL url = ResourceUtils.getURL(resource);
        Object obj = url.getContent();
        return (obj instanceof InputStream) ? IOUtils.toString((InputStream) obj) : "";
    }

    @Override
    public String getSubjects() throws IOException {
        if (subjects == null)
            subjects = jsonLoader(url_subjects);
        return subjects;
    }

    @Override
    public String getScopes() throws IOException {
        if (scopes == null)
            scopes = jsonLoader(url_scopes);
        return scopes;
    }

    @Override
    public String getPrivileges() throws IOException {
        if (privileges == null)
            privileges = jsonLoader(url_privileges);
        return privileges;
    }

    @Override
    public String getAssignments() throws IOException {
        if (assignments == null)
            assignments = jsonLoader(url_assignments);
        return assignments;
    }

    @Override
    public String putAssignment(String jsonBody) throws IOException {
        return jsonBody;
    }
}
