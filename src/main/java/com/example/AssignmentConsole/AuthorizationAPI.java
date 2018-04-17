package com.example.AssignmentConsole;

import java.io.IOException;

public interface AuthorizationAPI {
    String getSubjects() throws IOException;

    String getScopes() throws IOException;

    String getPrivileges() throws IOException;

    String getAssignments() throws IOException;

    String putAssignment(final String jsonBody) throws IOException;
}
