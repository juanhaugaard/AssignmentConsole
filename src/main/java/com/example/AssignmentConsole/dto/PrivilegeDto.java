package com.example.AssignmentConsole.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class PrivilegeDto {
    private String id;
    private String name;
    private String system;
    private String object;
    private String action;
}
