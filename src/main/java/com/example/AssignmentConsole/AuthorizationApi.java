package com.example.AssignmentConsole;

import com.example.AssignmentConsole.dto.AssignmentDto;
import com.example.AssignmentConsole.dto.PrivilegeDto;
import com.example.AssignmentConsole.dto.RoleDto;
import com.example.AssignmentConsole.dto.ScopeDto;
import com.example.AssignmentConsole.dto.ScopeTypeDto;
import com.example.AssignmentConsole.dto.SubjectDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class AuthorizationApi implements AuthorizationAPI {
    private static String MSG = "mapping {}: {}";
    private AuthMapper mapper;
    private List<SubjectDto> subjectList;
    private List<RoleDto> roleList;
    private List<ScopeDto> scopeList;
    private List<ScopeTypeDto> scopeTypeList;
    private List<PrivilegeDto> privilegeList;
    private List<AssignmentDto> assignmentList;

    @Autowired
    private JsonAccess jsonAccess;

    public AuthorizationApi() {
        mapper = new AuthMapper();
    }

    @Override
    public String getSubjects() {
        AUTH_TYPE type = AUTH_TYPE.subjects;
        try {
            if (subjectList == null)
                subjectList = mapper.deserializeSubjects(jsonAccess.jsonGet(type));
            return mapper.serializeSubjects(subjectList);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            subjectList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    public String getScopes() {
        AUTH_TYPE type = AUTH_TYPE.scopes;
        try {
            if (scopeList == null)
                scopeList = mapper.deserializeScopes(jsonAccess.jsonGet(type));
            return mapper.serializeScopes(scopeList);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            scopeList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    public String getRoles() {
        AUTH_TYPE type = AUTH_TYPE.roles;
        try {
            if (roleList == null)
                roleList = mapper.deserializeRoles(jsonAccess.jsonGet(type));
            return mapper.serializeRoles(roleList);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            roleList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    public String getScopeTypes() {
        AUTH_TYPE type = AUTH_TYPE.scopeTypes;
        try {
            if (scopeTypeList == null)
                scopeTypeList = mapper.deserializeScopeTypes(jsonAccess.jsonGet(type));
            return mapper.serializeScopeTypes(scopeTypeList);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            scopeTypeList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    public String getPrivileges() {
        AUTH_TYPE type = AUTH_TYPE.privileges;
        try {
            if (privilegeList == null)
                privilegeList = mapper.deserializePrivileges(jsonAccess.jsonGet(type));
            return mapper.serializePrivileges(privilegeList);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            privilegeList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    public String getAssignments() {
        AUTH_TYPE type = AUTH_TYPE.assignments;
        try {
            if (assignmentList == null)
                assignmentList = mapper.deserializeAssignments(jsonAccess.jsonGet(type));
            List<AssignmentDto.Summary> summaries = mapper.assignmentsToSummaries(assignmentList);
            return mapper.serializeAssignmentSummaries(summaries);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            assignmentList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    public String putAssignment(final String jsonBody) {
        AUTH_TYPE type = AUTH_TYPE.assignments;
        try {
            return jsonAccess.jsonPut(type, jsonBody);
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
}
