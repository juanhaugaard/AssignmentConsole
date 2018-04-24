package com.example.AssignmentConsole;


import com.example.AssignmentConsole.dto.AssignmentDto;
import com.example.AssignmentConsole.dto.PrivilegeDto;
import com.example.AssignmentConsole.dto.ScopeDto;
import com.example.AssignmentConsole.dto.ScopeTypeDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Profile({"stubs"})
public class AuthorizationApiResource implements AuthorizationAPI {
    public static final String JSON_EMPTY_ARRAY = "[]";
    public final String url_subjects = "classpath:subjects.json";
    public final String url_scopes = "classpath:scopes.json";
    public final String url_scopeTypes = "classpath:scopetypes.json";
    public final String url_privileges = "classpath:privileges.json";
    public final String url_assignments = "classpath:assignments.json";
    private Moshi moshi;
    private Type subjectType;
    private Type scopeType;
    private Type scopeTypeType;
    private Type privilegeType;
    private Type assignmentType;
    private Type assignmentSummaryType;
    private List<SubjectDto> subjectList;
    private List<ScopeDto> scopeList;
    private List<ScopeTypeDto> scopeTypeList;
    private List<PrivilegeDto> privilegeList;
    private List<AssignmentDto> assignmentList;

    public AuthorizationApiResource() {
        log.info("constructing {}", this.getClass().getSimpleName());
        moshi = new Moshi.Builder().build();
        subjectType = Types.newParameterizedType(List.class, SubjectDto.class);
        scopeType = Types.newParameterizedType(List.class, ScopeDto.class);
        scopeTypeType = Types.newParameterizedType(List.class, ScopeTypeDto.class);
        privilegeType = Types.newParameterizedType(List.class, PrivilegeDto.class);
        assignmentType = Types.newParameterizedType(List.class, AssignmentDto.class);
        assignmentSummaryType = Types.newParameterizedType(List.class, AssignmentDto.Summary.class);
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
            return (subjectList != null) ? serializeSubjects(subjectList) : JSON_EMPTY_ARRAY;
        } catch (IOException e) {
            log.error("serializing subjects: {}", e.getMessage());
        }
        return JSON_EMPTY_ARRAY;
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
            return (scopeList != null) ? serializeScopes(scopeList) : JSON_EMPTY_ARRAY;
        } catch (IOException e) {
            log.error("serializing scopes: {}", e.getMessage());
        }
        return JSON_EMPTY_ARRAY;
    }

    @Override
    public String getScopeTypes() {
        if (scopeTypeList == null) {
            try {
                scopeTypeList = deserializeScopeTypes(jsonLoader(url_scopeTypes));
            } catch (IOException e) {
                log.error("deserializing scope types: {}", e.getMessage());
                scopeTypeList = null;
            }
        }
        try {
            return (scopeTypeList != null) ? serializeScopeTypes(scopeTypeList) : JSON_EMPTY_ARRAY;
        } catch (IOException e) {
            log.error("serializing scope types: {}", e.getMessage());
        }
        return JSON_EMPTY_ARRAY;
    }

    @Override
    public String getPrivileges() {
        if (privilegeList == null) {
            try {
                privilegeList = deserializePrivileges(jsonLoader(url_privileges));
            } catch (IOException e) {
                log.error("deserializing privileges: {}", e.getMessage());
                privilegeList = null;
            }
        }
        try {
            return (privilegeList != null) ? serializePrivileges(privilegeList) : JSON_EMPTY_ARRAY;
        } catch (IOException e) {
            log.error("serializing privileges: {}", e.getMessage());
        }
        return JSON_EMPTY_ARRAY;
    }

    @Override
    public String getAssignments() {
        String ret = JSON_EMPTY_ARRAY;
        if (assignmentList == null) {
            try {
                assignmentList = deserializeAssignments(jsonLoader(url_assignments));
            } catch (IOException e) {
                log.error("deserializing assignments: {}", e.getMessage());
                assignmentList = null;
            }
        }
        try {
            if (assignmentList != null) {
                List<AssignmentDto.Summary> summaries = assignmentsToSummaries(assignmentList);
                ret = serializeAssignmentSummaries(summaries);
            }
        } catch (IOException e) {
            log.error("serializing privileges: {}", e.getMessage());
        }
        return ret;
    }

    @Override
    public String putAssignment(String jsonBody) {
        log.info("putAssignment() invoked, json: {}", jsonBody);
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

    private List<ScopeTypeDto> deserializeScopeTypes(final String json) throws IOException {
        JsonAdapter<List<ScopeTypeDto>> jsonAdapter = moshi.adapter(scopeTypeType);
        return jsonAdapter.fromJson(json);
    }

    private String serializeScopeTypes(final List<ScopeTypeDto> scopetypes) throws IOException {
        JsonAdapter<List<ScopeTypeDto>> jsonAdapter = moshi.adapter(scopeTypeType);
        return jsonAdapter.toJson(scopetypes);
    }

    private List<PrivilegeDto> deserializePrivileges(final String json) throws IOException {
        JsonAdapter<List<PrivilegeDto>> jsonAdapter = moshi.adapter(privilegeType);
        return jsonAdapter.fromJson(json);
    }

    private String serializePrivileges(final List<PrivilegeDto> privileges) throws IOException {
        JsonAdapter<List<PrivilegeDto>> jsonAdapter = moshi.adapter(privilegeType);
        return jsonAdapter.toJson(privileges);
    }

    private List<AssignmentDto> deserializeAssignments(final String json) throws IOException {
        JsonAdapter<List<AssignmentDto>> jsonAdapter = moshi.adapter(assignmentType);
        return jsonAdapter.fromJson(json);
    }

    private String serializeAssignments(final List<AssignmentDto> assignments) throws IOException {
        JsonAdapter<List<AssignmentDto>> jsonAdapter = moshi.adapter(assignmentType);
        return jsonAdapter.toJson(assignments);
    }

    private String serializeAssignmentSummaries(final List<AssignmentDto.Summary> assignmentSummaries) throws IOException {
        JsonAdapter<List<AssignmentDto.Summary>> jsonAdapter = moshi.adapter(assignmentSummaryType);
        return jsonAdapter.toJson(assignmentSummaries);
    }

    private List<AssignmentDto.Summary> assignmentsToSummaries(final List<AssignmentDto> assignments) {
        List<AssignmentDto.Summary> ret = new ArrayList<>();
        ret.addAll(assignments
            .stream()
            .map(a->a.toSummary())
            .collect(Collectors.toList()));
        return ret;
    }
}

// private List<SubjectDto> deserializeSubjects(final String json) throws IOException {
//   // JsonAdapter<List<SubjectDto>> jsonAdapter = moshi.adapter(subjectType);
//   Type subjectType = Types.newParameterizedType(List.class, Object.class);
//   JsonAdapter<List<Map<String,Object>>> jsonAdapter = moshi.adapter(subjectType);
//   List<Map<String,Object>> ret = jsonAdapter.fromJson(json);
//   Map<String,Object> item = ret.get(0);
//   String key;
//   key = "identifier";
//   if (item.containsKey(key)) {
//     Object value = item.get(key);
//     log.info("{}: {}", key, value);
//   } else {
//     log.warn("key {} not found", key);
//   }
//   key = "type";
//   if (item.containsKey(key)) {
//     Object value = item.get(key);
//     log.info("{}: {}", key, value);
//   } else {
//     log.warn("key {} not found", key);
//   }
//   return null;
// }
