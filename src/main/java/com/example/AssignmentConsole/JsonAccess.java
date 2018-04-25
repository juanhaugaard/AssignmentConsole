/*
 * Copyright (c) 2018. Dovel Technologies and Digital Infuzion.
 */

package com.example.AssignmentConsole;

import java.io.IOException;

public interface JsonAccess {
    String jsonGet(AUTH_TYPE type) throws IOException;

    String jsonPut(AUTH_TYPE type, String jsonBody) throws IOException;
}
