package com.example.AssignmentConsole;

import com.example.AssignmentConsole.dto.AssignmentDto;
import com.example.AssignmentConsole.dto.PrivilegeDto;
import com.example.AssignmentConsole.dto.RoleDto;
import com.example.AssignmentConsole.dto.ScopeDto;
import com.example.AssignmentConsole.dto.ScopeTypeDto;
import com.example.AssignmentConsole.dto.SubjectDto;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AuthMapper {
    public static final String JSON_EMPTY_ARRAY = "[]";
    private Moshi moshi;
    private Type subjectType;
    private Type roleType;
    private Type scopeType;
    private Type scopeTypeType;
    private Type privilegeType;
    private Type assignmentType;
    private Type assignmentSummaryType;

    public AuthMapper() {
        log.info("constructing {}", this.getClass().getSimpleName());
        moshi = new Moshi.Builder().build();
        subjectType = Types.newParameterizedType(List.class, SubjectDto.class);
        roleType = Types.newParameterizedType(List.class, RoleDto.class);
        scopeType = Types.newParameterizedType(List.class, ScopeDto.class);
        scopeTypeType = Types.newParameterizedType(List.class, ScopeTypeDto.class);
        privilegeType = Types.newParameterizedType(List.class, PrivilegeDto.class);
        assignmentType = Types.newParameterizedType(List.class, AssignmentDto.class);
        assignmentSummaryType = Types.newParameterizedType(List.class, AssignmentDto.Summary.class);
    }

    public List<?> deserialize(final String json, AUTH_TYPE type) throws IOException {
        List<?> ret = new ArrayList();
        if (json == null || type == null)
            return ret;
        switch (type) {
            case subjects:
                ret = deserializeSubjects(json);
                break;
            case privileges:
                ret = deserializePrivileges(json);
                break;
            case roles:
                ret = deserializeRoles(json);
                break;
            case scopes:
                ret = deserializeScopes(json);
                break;
            case scopeTypes:
                ret = deserializeScopeTypes(json);
                break;
            case assignments:
                ret = deserializeAssignments(json);
                break;
            default:
                throw new IllegalArgumentException("Not implemented Authorization type: " + type);
        }
        return ret;
    }

    public String serialize(List<?> list, AUTH_TYPE type) throws IOException {
        String ret = JSON_EMPTY_ARRAY;
        if (list == null || type == null)
            return ret;
        switch (type) {
            case subjects:
                ret = serializeSubjects((List<SubjectDto>) list);
                break;
            case privileges:
                ret = serializePrivileges((List<PrivilegeDto>) list);
                break;
            case roles:
                ret = serializeRoles((List<RoleDto>) list);
                break;
            case scopes:
                ret = serializeScopes((List<ScopeDto>) list);
                break;
            case scopeTypes:
                ret = serializeScopeTypes((List<ScopeTypeDto>) list);
                break;
            case assignments:
                ret = serializeAssignments((List<AssignmentDto>) list);
                break;
            default:
                throw new IllegalArgumentException("Not implemented Authorization type: " + type);
        }
        return ret;
    }

    private List<SubjectDto> deserializeSubjects(final String json) throws IOException {
        JsonAdapter<List<SubjectDto>> jsonAdapter = moshi.adapter(subjectType);
        return jsonAdapter.fromJson(json);
    }

    private String serializeSubjects(final List<SubjectDto> subjects) {
        JsonAdapter<List<SubjectDto>> jsonAdapter = moshi.adapter(subjectType);
        return jsonAdapter.toJson(subjects);
    }

    private List<ScopeDto> deserializeScopes(final String json) throws IOException {
        JsonAdapter<List<ScopeDto>> jsonAdapter = moshi.adapter(scopeType);
        return jsonAdapter.fromJson(json);
    }

    private String serializeScopes(final List<ScopeDto> scopes) {
        JsonAdapter<List<ScopeDto>> jsonAdapter = moshi.adapter(scopeType);
        return jsonAdapter.toJson(scopes);
    }

    private List<RoleDto> deserializeRoles(final String json) throws IOException {
        JsonAdapter<List<RoleDto>> jsonAdapter = moshi.adapter(roleType);
        return jsonAdapter.fromJson(json);
    }

    private String serializeRoles(final List<RoleDto> roles) {
        JsonAdapter<List<RoleDto>> jsonAdapter = moshi.adapter(roleType);
        return jsonAdapter.toJson(roles);
    }

    private List<ScopeTypeDto> deserializeScopeTypes(final String json) throws IOException {
        JsonAdapter<List<ScopeTypeDto>> jsonAdapter = moshi.adapter(scopeTypeType);
        return jsonAdapter.fromJson(json);
    }

    private String serializeScopeTypes(final List<ScopeTypeDto> scopetypes) {
        JsonAdapter<List<ScopeTypeDto>> jsonAdapter = moshi.adapter(scopeTypeType);
        return jsonAdapter.toJson(scopetypes);
    }

    private List<PrivilegeDto> deserializePrivileges(final String json) throws IOException {
        JsonAdapter<List<PrivilegeDto>> jsonAdapter = moshi.adapter(privilegeType);
        return jsonAdapter.fromJson(json);
    }

    private String serializePrivileges(final List<PrivilegeDto> privileges) {
        JsonAdapter<List<PrivilegeDto>> jsonAdapter = moshi.adapter(privilegeType);
        return jsonAdapter.toJson(privileges);
    }

    private List<AssignmentDto> deserializeAssignments(final String json) throws IOException {
        JsonAdapter<List<AssignmentDto>> jsonAdapter = moshi.adapter(assignmentType);
        return jsonAdapter.fromJson(json);
    }

    private String serializeAssignments(final List<AssignmentDto> assignments) {
        JsonAdapter<List<AssignmentDto>> jsonAdapter = moshi.adapter(assignmentType);
        return jsonAdapter.toJson(assignments);
    }

    public String serializeAssignmentSummaries(final List<AssignmentDto.Summary> assignmentSummaries) {
        JsonAdapter<List<AssignmentDto.Summary>> jsonAdapter = moshi.adapter(assignmentSummaryType);
        return jsonAdapter.toJson(assignmentSummaries);
    }

    public List<AssignmentDto.Summary> assignmentsToSummaries(final List<AssignmentDto> assignments) {
        List<AssignmentDto.Summary> ret = new ArrayList<>();
        ret.addAll(assignments
                .stream()
                .map(a -> a.toSummary())
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
