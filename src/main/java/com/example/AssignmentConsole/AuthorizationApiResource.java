package com.example.AssignmentConsole;


import com.example.AssignmentConsole.dto.AssignmentDto;
import com.example.AssignmentConsole.dto.PrivilegeDto;
import com.example.AssignmentConsole.dto.RoleDto;
import com.example.AssignmentConsole.dto.ScopeDto;
import com.example.AssignmentConsole.dto.ScopeTypeDto;
import com.example.AssignmentConsole.dto.SubjectDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import spark.utils.IOUtils;
import spark.utils.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Slf4j
@Component
@Profile({"stubs"})
public class AuthorizationApiResource implements AuthorizationAPI {
    private static String MSG = "mapping {}: {}";
    private AuthMapper mapper;
    private List<SubjectDto> subjectList;
    private List<RoleDto> roleList;
    private List<ScopeDto> scopeList;
    private List<ScopeTypeDto> scopeTypeList;
    private List<PrivilegeDto> privilegeList;
    private List<AssignmentDto> assignmentList;

    public AuthorizationApiResource() {
        log.info("constructing {}", this.getClass().getSimpleName());
        mapper = new AuthMapper();
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getSubjects() {
        AUTH_TYPE type = AUTH_TYPE.subjects;
        try {
            if (subjectList == null)
                subjectList = (List<SubjectDto>) mapper.deserialize(jsonLoader(type.resource()), type);
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
        try {
            if (scopeList == null)
                scopeList = (List<ScopeDto>) mapper.deserialize(jsonLoader(type.resource()), type);
            return mapper.serialize(scopeList, type);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            scopeList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getScopeTypes() {
        AUTH_TYPE type = AUTH_TYPE.scopeTypes;
        try {
            if (scopeTypeList == null)
                scopeTypeList = (List<ScopeTypeDto>) mapper.deserialize(jsonLoader(type.resource()), type);
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
        try {
            if (privilegeList == null)
                privilegeList = (List<PrivilegeDto>) mapper.deserialize(jsonLoader(type.resource()), type);
            return mapper.serialize(privilegeList, type);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            privilegeList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getRoles() {
        AUTH_TYPE type = AUTH_TYPE.roles;
        try {
            if (roleList == null)
                roleList = (List<RoleDto>) mapper.deserialize(jsonLoader(type.resource()), type);
            return mapper.serialize(roleList, type);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            roleList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getAssignments() {
        AUTH_TYPE type = AUTH_TYPE.assignments;
        try {
            if (assignmentList == null)
                assignmentList = (List<AssignmentDto>) mapper.deserialize(jsonLoader(type.resource()), type);
            List<AssignmentDto.Summary> summaries = mapper.assignmentsToSummaries(assignmentList);
            return mapper.serializeAssignmentSummaries(summaries);
        } catch (IOException e) {
            log.error(MSG, type, e.getMessage());
            assignmentList = null;
        }
        return AuthMapper.JSON_EMPTY_ARRAY;
    }

    @Override
    public String putAssignment(String jsonBody) {
        log.info("putAssignment() invoked, json: {}", jsonBody);
        return jsonBody;
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
}
