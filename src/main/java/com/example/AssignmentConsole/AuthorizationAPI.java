package com.example.AssignmentConsole;

public interface AuthorizationAPI {
    String getSubjects();

    String getScopes();

    String getScopeTypes();

    String getPrivileges();

    String getAssignments();

    String putAssignment(final String jsonBody);
}
