package com.example.AssignmentConsole.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ScopeDto {
    private String id;
    private String name;
    private String type;
    private Parent parent;

    @Data
    @ToString
    @EqualsAndHashCode
    public static class Parent {
        private String id;
        private String name;
    }
}
