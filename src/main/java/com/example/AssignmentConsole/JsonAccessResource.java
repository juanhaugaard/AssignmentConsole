/*
 * Copyright (c) 2018. Dovel Technologies and Digital Infuzion.
 */

package com.example.AssignmentConsole;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import spark.utils.IOUtils;
import spark.utils.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Slf4j
@Component
@Profile({"stubs"})
public class JsonAccessResource implements JsonAccess {

    public JsonAccessResource() {
        log.info("constructing {}", this.getClass().getSimpleName());
    }

    @PostConstruct
    public void init() {

    }

    @Override
    public String jsonGet(AUTH_TYPE type) throws IOException {
        String ret = null;
        String resource = type.resource();
        log.debug("executing GET {}", resource);
        try {
            URL url = ResourceUtils.getURL(resource);
            Object obj = url.getContent();
            ret = (obj instanceof InputStream) ? IOUtils.toString((InputStream) obj) : "";
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return ret;
    }

    @Override
    public String jsonPut(AUTH_TYPE type, String jsonBody) throws IOException {
        log.info("put {} invoked, json: {}", type, jsonBody);
        return jsonBody;
    }
}
