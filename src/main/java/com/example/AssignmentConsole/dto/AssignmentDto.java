package com.example.AssignmentConsole.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@ToString
@EqualsAndHashCode
public class AssignmentDto {
    private String id;
    private List<ScopeType> scopes;
    private List<PrivilegeType> privileges;
    private List<RoleType> roles;
    private String expdate;
    private String effdate;
    private String delegated;
    private String assignee;
    private String assignor;
    private String delegatee;
    private String delegator;

    public static Summary toSummary(AssignmentDto assignment) {
        Summary ret = new Summary();
        ret.setId(assignment.getId());
        ret.setExpdate(assignment.getExpdate());
        ret.setEffdate(assignment.getEffdate());
        ret.setDelegated(Boolean.parseBoolean(assignment.getDelegated()));
        ret.setAssignee(ret.getDelegated() ? assignment.getDelegatee() : assignment.getAssignee());
        ret.setAssignor(ret.getDelegated() ? assignment.getDelegator() : assignment.getAssignor());

        List<ScopeType> scopes = assignment.getScopes();
        if (scopes != null && !scopes.isEmpty()) {
            ret.getScopes().addAll(scopes
                    .stream()
                    .map(s -> s.id)
                    .collect(Collectors.toList()));
        }

        List<PrivilegeType> privileges = assignment.getPrivileges();
        if (privileges != null && !privileges.isEmpty()) {
            ret.getPrivileges().addAll(privileges
                    .stream()
                    .map(p -> p.id)
                    .collect(Collectors.toList()));
        }

        List<RoleType> roles = assignment.getRoles();
        if (roles != null && !roles.isEmpty()) {
            ret.getRoles().addAll(roles
                    .stream()
                    .map(r -> r.id)
                    .collect(Collectors.toList()));
        }
        return ret;
    }

    public Summary toSummary() {
        return toSummary(this);
    }

    @Data
    @ToString
    @EqualsAndHashCode
    public static class ScopeType {
        private String id;
        private String name;
    }

    @Data
    @ToString
    @EqualsAndHashCode
    public static class PrivilegeType {
        private Long id;
        private String name;
    }

    @Data
    @ToString
    @EqualsAndHashCode
    public static class RoleType {
        private Long id;
        private String name;
        private List<PrivilegeType> privileges;
        private List<PrivilegeType> delegableprivileges;
    }

    @Data
    @ToString
    @EqualsAndHashCode
    public static class Summary {
        private String id;
        private List<String> scopes;
        private List<Long> privileges;
        private List<Long> roles;
        private String expdate;
        private String effdate;
        private Boolean delegated;
        private String assignee;
        private String assignor;

        public Summary() {
            roles = new ArrayList<>();
            scopes = new ArrayList<>();
            privileges = new ArrayList<>();
        }
    }
}
