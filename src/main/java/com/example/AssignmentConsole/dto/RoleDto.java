package com.example.AssignmentConsole.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@EqualsAndHashCode
public class RoleDto {
    private Long id;
    private String name;
    private List<PrivilegeDto> privileges;
    private List<PrivilegeDto> delegableprivileges;

    public RoleDto() {
        privileges = new ArrayList<>();
        delegableprivileges = new ArrayList<>();
    }
}
