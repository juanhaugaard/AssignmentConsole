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

    private String jsonLoader(final String resource) {
        String ret = null;
        log.debug("executing GET {}" , resource);
        try {
            URL url = ResourceUtils.getURL(resource);
            Object obj = url.getContent();
            ret = (obj instanceof InputStream) ? IOUtils.toString((InputStream) obj) : "";
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return ret;
    }

    @Override
    public String getSubjects() {
        if (subjects == null)
            subjects = jsonLoader(url_subjects);
        return subjects;
    }

    @Override
    public String getScopes() {
        if (scopes == null)
            scopes = jsonLoader(url_scopes);
        return scopes;
    }

    @Override
    public String getPrivileges() {
        if (privileges == null)
            privileges = jsonLoader(url_privileges);
        return privileges;
    }

    @Override
    public String getAssignments() {
        if (assignments == null)
            assignments = jsonLoader(url_assignments);
        return assignments;
    }

    @Override
    public String putAssignment(String jsonBody) {
        return jsonBody;
    }
}
