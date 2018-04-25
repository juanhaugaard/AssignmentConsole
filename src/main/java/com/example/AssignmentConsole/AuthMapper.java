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

    public List<SubjectDto> deserializeSubjects(final String json) throws IOException {
        JsonAdapter<List<SubjectDto>> jsonAdapter = moshi.adapter(subjectType);
        return jsonAdapter.fromJson(json);
    }

    public String serializeSubjects(final List<SubjectDto> list) {
        if (list == null) return JSON_EMPTY_ARRAY;
        JsonAdapter<List<SubjectDto>> jsonAdapter = moshi.adapter(subjectType);
        return jsonAdapter.toJson(list);
    }

    public List<ScopeDto> deserializeScopes(final String json) throws IOException {
        JsonAdapter<List<ScopeDto>> jsonAdapter = moshi.adapter(scopeType);
        return jsonAdapter.fromJson(json);
    }

    public String serializeScopes(final List<ScopeDto> list) {
        if (list == null) return JSON_EMPTY_ARRAY;
        JsonAdapter<List<ScopeDto>> jsonAdapter = moshi.adapter(scopeType);
        return jsonAdapter.toJson(list);
    }

    public List<RoleDto> deserializeRoles(final String json) throws IOException {
        JsonAdapter<List<RoleDto>> jsonAdapter = moshi.adapter(roleType);
        return jsonAdapter.fromJson(json);
    }

    public String serializeRoles(final List<RoleDto> list) {
        if (list == null) return JSON_EMPTY_ARRAY;
        JsonAdapter<List<RoleDto>> jsonAdapter = moshi.adapter(roleType);
        return jsonAdapter.toJson(list);
    }

    public List<ScopeTypeDto> deserializeScopeTypes(final String json) throws IOException {
        JsonAdapter<List<ScopeTypeDto>> jsonAdapter = moshi.adapter(scopeTypeType);
        return jsonAdapter.fromJson(json);
    }

    public String serializeScopeTypes(final List<ScopeTypeDto> list) {
        if (list == null) return JSON_EMPTY_ARRAY;
        JsonAdapter<List<ScopeTypeDto>> jsonAdapter = moshi.adapter(scopeTypeType);
        return jsonAdapter.toJson(list);
    }

    public List<PrivilegeDto> deserializePrivileges(final String json) throws IOException {
        JsonAdapter<List<PrivilegeDto>> jsonAdapter = moshi.adapter(privilegeType);
        return jsonAdapter.fromJson(json);
    }

    public String serializePrivileges(final List<PrivilegeDto> list) {
        if (list == null) return JSON_EMPTY_ARRAY;
        JsonAdapter<List<PrivilegeDto>> jsonAdapter = moshi.adapter(privilegeType);
        return jsonAdapter.toJson(list);
    }

    public List<AssignmentDto> deserializeAssignments(final String json) throws IOException {
        JsonAdapter<List<AssignmentDto>> jsonAdapter = moshi.adapter(assignmentType);
        return jsonAdapter.fromJson(json);
    }

    public String serializeAssignments(final List<AssignmentDto> list) {
        if (list == null) return JSON_EMPTY_ARRAY;
        JsonAdapter<List<AssignmentDto>> jsonAdapter = moshi.adapter(assignmentType);
        return jsonAdapter.toJson(list);
    }

    public String serializeAssignmentSummaries(final List<AssignmentDto.Summary> list) {
        if (list == null) return JSON_EMPTY_ARRAY;
        JsonAdapter<List<AssignmentDto.Summary>> jsonAdapter = moshi.adapter(assignmentSummaryType);
        return jsonAdapter.toJson(list);
    }

    public List<AssignmentDto.Summary> assignmentsToSummaries(final List<AssignmentDto> list) {
        List<AssignmentDto.Summary> ret = new ArrayList<>();
        if (list != null) {
            ret.addAll(list
                    .stream()
                    .map(a -> a.toSummary())
                    .collect(Collectors.toList()));
        }
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
