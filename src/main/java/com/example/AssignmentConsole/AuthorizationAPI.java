package com.example.AssignmentConsole;

import java.io.IOException;

public interface AuthorizationAPI {
    String getSubjects();

    String getScopes();

    String getPrivileges();

    String getAssignments();

    String putAssignment(final String jsonBody);
}
