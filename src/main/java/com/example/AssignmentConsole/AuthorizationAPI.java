package com.example.AssignmentConsole;

import java.io.IOException;

public interface AuthorizationAPI {
    String getUrl_subjects() throws IOException;

    String getScopes() throws IOException;

    String getPrivileges() throws IOException;

    String getAssignments() throws IOException;
}
