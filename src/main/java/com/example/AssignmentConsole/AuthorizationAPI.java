package com.example.AssignmentConsole;

import java.io.IOException;

public interface AuthorizationAPI {
    String getSubjects() throws IOException;

    String getAssignments() throws IOException;
}
