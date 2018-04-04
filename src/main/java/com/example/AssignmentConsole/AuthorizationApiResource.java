package com.example.AssignmentConsole;


import org.springframework.stereotype.Component;
import spark.utils.IOUtils;
import spark.utils.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Component
public class AuthorizationApiResource implements AuthorizationAPI {
    public final String subjects = "classpath:subjects.json";
    public final String assignments = "classpath:assignments.json";

    private String jsonLoader(final String resource) throws IOException {
        URL url = ResourceUtils.getURL(resource);
        Object obj = url.getContent();
        return (obj instanceof InputStream)?IOUtils.toString((InputStream)obj):"";
    }

    @Override
    public String getSubjects() throws IOException {
        return jsonLoader(subjects);
    }

    @Override
    public String getAssignments() throws IOException {
        return jsonLoader(assignments);
    }
}
