package com.example.AssignmentConsole;


import com.example.AssignmentConsole.dto.AssignmentDto;
import com.example.AssignmentConsole.dto.PrivilegeDto;
import com.example.AssignmentConsole.dto.RoleDto;
import com.example.AssignmentConsole.dto.ScopeDto;
import com.example.AssignmentConsole.dto.ScopeTypeDto;
import com.example.AssignmentConsole.dto.SubjectDto;
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
import java.util.List;

@Slf4j
@Component
@Profile({"!stubs"})
public class AuthorizationApiRest implements AuthorizationAPI {
    private static String MSG = "mapping {}: {}";
    public final MediaType JSON;
    private final OkHttpClient client = new OkHttpClient();
    private AuthMapper mapper;
    private List<SubjectDto> subjectList;
    private List<RoleDto> roleList;
    private List<ScopeDto> scopeList;
    private List<ScopeTypeDto> scopeTypeList;
    private List<PrivilegeDto> privilegeList;
    private List<AssignmentDto> assignmentList;
    @Value("${authorization-domain}")
    private String domainAuthorization;
    @Value("${token-prefix}")
    private String tokenPrefix;
    @Value("${token-value}")
    private String tokenValue;
    private String TOKEN;

    public AuthorizationApiRest() {
        log.info("constructing {}", this.getClass().getSimpleName());
        JSON = MediaType.parse("application/json; charset=utf-8");
        mapper = new AuthMapper();
    }

    @PostConstruct
    public void init() {
        TOKEN = String.format("%s %s", tokenPrefix, tokenValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getSubjects() {
        AUTH_TYPE type = AUTH_TYPE.subjects;
        String url = domainAuthorization + type.url();
        try {
            if (subjectList == null)
                subjectList = (List<SubjectDto>) mapper.deserialize(restGet(url), type);
            return mapper.serialize(subjectList, type);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            subjectList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getScopes() {
        AUTH_TYPE type = AUTH_TYPE.scopes;
        String url = domainAuthorization + type.url();
        try {
            if (scopeList == null)
                scopeList = (List<ScopeDto>) mapper.deserialize(restGet(url), type);
            return mapper.serialize(scopeList, type);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            scopeList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getRoles() {
        AUTH_TYPE type = AUTH_TYPE.roles;
        String url = domainAuthorization + type.url();
        try {
            if (roleList == null)
                roleList = (List<RoleDto>) mapper.deserialize(restGet(url), type);
            return mapper.serialize(roleList, type);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            roleList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getScopeTypes() {
        AUTH_TYPE type = AUTH_TYPE.scopeTypes;
        String url = domainAuthorization + type.url();
        try {
            if (scopeTypeList == null)
                scopeTypeList = (List<ScopeTypeDto>) mapper.deserialize(restGet(url), type);
            return mapper.serialize(scopeTypeList, type);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            scopeTypeList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getPrivileges() {
        AUTH_TYPE type = AUTH_TYPE.privileges;
        String url = domainAuthorization + type.url();
        try {
            if (privilegeList == null)
                privilegeList = (List<PrivilegeDto>) mapper.deserialize(restGet(url), type);
            return mapper.serialize(privilegeList, type);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            privilegeList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getAssignments() {
        AUTH_TYPE type = AUTH_TYPE.assignments;
        String url = domainAuthorization + type.url();
        try {
            if (assignmentList == null)
                assignmentList = (List<AssignmentDto>) mapper.deserialize(restGet(url), type);
            List<AssignmentDto.Summary> summaries = mapper.assignmentsToSummaries(assignmentList);
            return mapper.serializeAssignmentSummaries(summaries);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            assignmentList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String putAssignment(final String jsonBody) {
        AUTH_TYPE type = AUTH_TYPE.assignments;
        String url = domainAuthorization + type.url();
        try {
            return restPut(url, jsonBody);
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public String evictCache() {
        log.debug("Evicting caches");
        subjectList = null;
        scopeList = null;
        roleList = null;
        scopeTypeList = null;
        privilegeList = null;
        assignmentList = null;
        return "Caches cleared";
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
            if (response.isSuccessful() && (response.body() != null)) {
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
            if (response.isSuccessful() && (response.body() != null)) {
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
