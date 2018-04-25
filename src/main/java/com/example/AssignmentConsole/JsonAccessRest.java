package com.example.AssignmentConsole;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Component
@Profile({"!stubs"})
public class JsonAccessRest implements JsonAccess {
    public final MediaType JSON;
    private final OkHttpClient client;
    @Value("${authorization-domain}")
    private String domainAuthorization;
    @Value("${token-prefix}")
    private String tokenPrefix;
    @Value("${token-value}")
    private String tokenValue;
    private String TOKEN;

    public JsonAccessRest() {
        log.info("constructing {}", this.getClass().getSimpleName());
        client = new OkHttpClient();
        JSON = MediaType.parse("application/json; charset=utf-8");
    }

    @PostConstruct
    public void init() {
        TOKEN = String.format("%s %s", tokenPrefix, tokenValue);
    }

    @Override
    public String jsonGet(AUTH_TYPE type) throws IOException {
        String url = domainAuthorization + type.url();
        Request request = new Request.Builder()
                .addHeader("Authorization", TOKEN)
                .addHeader("Accept", JSON.toString())
                .get()
                .url(url)
                .build();
        JsonAccessRest.log.debug("executing {}, headers: {}", request, request.headers());
        IOException internal;
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && (response.body() != null)) {
                String body = response.body().string();
                JsonAccessRest.log.debug("result code: {}, headers: {}, body: {}", response.code(), response.headers(), body);
                return body;
            } else {
                String body = String.format("{\"code\": %d, \"message\": \"%s, %s\"}",
                        response.code(), response.message(), url);
                internal = new IOException(body);
            }
        } catch (IOException e) {
            String msg = String.format("{\"code\": %d, \"message\": \"%s\"}", 500, e.getMessage());
            throw new IOException(msg);
        }
        throw internal;
    }

    @Override
    public String jsonPut(AUTH_TYPE type, String jsonBody) throws IOException {
        String url = domainAuthorization + type.url();
        RequestBody requestBody = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .addHeader("Authorization", TOKEN)
                .addHeader("Accept", JSON.toString())
                .put(requestBody)
                .url(url)
                .build();
        JsonAccessRest.log.debug("executing {}, headers: {}", request, request.headers());
        IOException internal;
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && (response.body() != null)) {
                String body = response.body().string();
                JsonAccessRest.log.debug("result code: {}, headers: {}, body: {}", response.code(), response.headers(), body);
                return body;
            } else {
                String body = String.format("{\"code\": %d, \"message\": \"%s, %s\"}",
                        response.code(), response.message(), url);
                internal = new IOException(body);
            }
        } catch (IOException e) {
            String msg = String.format("{\"code\": %d, \"message\": \"%s\"}", 500, e.getMessage());
            throw new IOException(msg);
        }
        throw internal;
    }
}
