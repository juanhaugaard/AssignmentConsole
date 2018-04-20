package com.example.AssignmentConsole;

public interface AuthorizationAPI {
    String getSubjects();

    String getScopes();

    String getPrivileges();

    String getAssignments();

    String putAssignment(final String jsonBody);
}
