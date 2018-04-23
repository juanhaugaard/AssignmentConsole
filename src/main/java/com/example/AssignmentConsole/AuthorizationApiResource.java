package com.example.AssignmentConsole;


import com.example.AssignmentConsole.dto.ScopeDto;
import com.example.AssignmentConsole.dto.SubjectDto;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import spark.utils.IOUtils;
import spark.utils.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

@Slf4j
@Component
@Profile({"stubs"})
public class AuthorizationApiResource implements AuthorizationAPI {
    public final String url_subjects = "classpath:subjects.json";
    public final String url_scopes = "classpath:scopes.json";
    public final String url_privileges = "classpath:privileges.json";
    public final String url_assignments = "classpath:assignments.json";
    private String privileges;
    private String assignments;
    private Moshi moshi;
    private Type subjectType;
    private Type scopeType;
    private List<SubjectDto> subjectList;
    private List<ScopeDto> scopeList;

    public AuthorizationApiResource() {
        log.info("constructing {}", this.getClass().getSimpleName());
        moshi = new Moshi.Builder().build();
        subjectType = Types.newParameterizedType(List.class, SubjectDto.class);
        scopeType = Types.newParameterizedType(List.class, ScopeDto.class);
    }

    @Override
    public String getSubjects() {
        if (subjectList == null) {
            try {
                subjectList = deserializeSubjects(jsonLoader(url_subjects));
            } catch (IOException e) {
                log.error("deserializing subjects: {}", e.getMessage());
                subjectList = null;
            }
        }
        try {
            return (subjectList != null) ? serializeSubjects(subjectList) : "[]";
        } catch (IOException e) {
            log.error("serializing subjects: {}", e.getMessage());
        }
        return "[]";
    }

    @Override
    public String getScopes() {
        if (scopeList == null) {
            try {
                scopeList = deserializeScopes(jsonLoader(url_scopes));
            } catch (IOException e) {
                log.error("deserializing scopes: {}", e.getMessage());
                scopeList = null;
            }
        }
        try {
            return (scopeList != null) ? serializeScopes(scopeList) : "[]";
        } catch (IOException e) {
            log.error("serializing scopes: {}", e.getMessage());
        }
        return "[]";
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

    private String jsonLoader(final String resource) {
        String ret = null;
        log.debug("executing GET {}", resource);
        try {
            URL url = ResourceUtils.getURL(resource);
            Object obj = url.getContent();
            ret = (obj instanceof InputStream) ? IOUtils.toString((InputStream) obj) : "";
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return ret;
    }

    private List<SubjectDto> deserializeSubjects(final String json) throws IOException {
        JsonAdapter<List<SubjectDto>> jsonAdapter = moshi.adapter(subjectType);
        return jsonAdapter.fromJson(json);
    }

    private String serializeSubjects(final List<SubjectDto> subjects) throws IOException {
        JsonAdapter<List<SubjectDto>> jsonAdapter = moshi.adapter(subjectType);
        return jsonAdapter.toJson(subjects);
    }

    private List<ScopeDto> deserializeScopes(final String json) throws IOException {
        JsonAdapter<List<ScopeDto>> jsonAdapter = moshi.adapter(scopeType);
        return jsonAdapter.fromJson(json);
    }

    private String serializeScopes(final List<ScopeDto> scopes) throws IOException {
        JsonAdapter<List<ScopeDto>> jsonAdapter = moshi.adapter(scopeType);
        return jsonAdapter.toJson(scopes);
    }
}
