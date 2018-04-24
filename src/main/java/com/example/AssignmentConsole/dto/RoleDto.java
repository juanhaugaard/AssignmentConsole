package com.example.AssignmentConsole.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
