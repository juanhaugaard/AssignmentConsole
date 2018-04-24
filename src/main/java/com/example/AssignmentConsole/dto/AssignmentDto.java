package com.example.AssignmentConsole.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString
@EqualsAndHashCode
public class AssignmentDto {
    private String id;
    private List<ScopeType> scopes;
    private List<PrivilegeType> privileges;
    private String expdate;
    private String effdate;
    private String delegated;
    private String assignee;
    private String assignor;
    private String delegatee;
    private String delegator;

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
    public static class Summary {
        private String id;
        private List<String> scopes;
        private List<Long> privileges;
        private String expdate;
        private String effdate;
        private Boolean delegated;
        private String assignee;
        private String assignor;
        public Summary() {
            scopes = new ArrayList<String>();
            privileges = new ArrayList<Long>();
        }
    }

    public Summary toSummary() {
        return toSummary(this);
    }

    public static Summary toSummary(AssignmentDto assignment) {
        Summary ret = new Summary();
        ret.setId(assignment.getId());
        ret.setExpdate(assignment.getExpdate());
        ret.setEffdate(assignment.getEffdate());
        ret.setDelegated(Boolean.parseBoolean(assignment.getDelegated()));
        ret.setAssignee(ret.getDelegated()?assignment.getDelegatee():assignment.getAssignee());
        ret.setAssignor(ret.getDelegated()?assignment.getDelegator():assignment.getAssignor());

        List<ScopeType> scopes = assignment.getScopes();
        if (scopes == null || scopes.isEmpty()) {
            log.warn("Scope list is empty");
        } else {
            ret.getScopes().addAll(assignment.getScopes()
                    .stream()
                    .map(s->s.id)
                    .collect(Collectors.toList()));
        }

        List<PrivilegeType> privileges = assignment.getPrivileges();
        if (privileges == null || privileges.isEmpty()) {
            log.warn("Privilege list is empty");
        } else {
            ret.getPrivileges().addAll(assignment.getPrivileges()
                    .stream()
                    .map(p -> p.id)
                    .collect(Collectors.toList()));
        }
        return ret;
    }
}
