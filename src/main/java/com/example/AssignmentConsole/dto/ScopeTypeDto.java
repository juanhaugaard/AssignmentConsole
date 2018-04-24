package com.example.AssignmentConsole.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ScopeTypeDto {
  private String scopetype;
  private String parent;
}
