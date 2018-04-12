package com.example.AssignmentConsole;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spark.Spark;

import javax.annotation.PostConstruct;
import java.nio.file.Paths;

@Slf4j
@Component
public class Server {
    private int port;
    private AuthorizationAPI api;

    public final String PATH_SUBJECTS = "/api/subjects";
    public final String PATH_ASSIGNMENTS = "/api/assignments";
    public final String PATH_PRIVILEGES = "/api/privileges";
    public final String PATH_SCOPES = "/api/scopes";

    @Autowired
    public Server(
            AuthorizationAPI api,
            @Value("${server-port}") int serverPort) {
        this.port = serverPort;
        this.api = api;
    }

    @PostConstruct
    public void start() {
        String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();
        currentDir = currentDir.replace('\\', '/');
        if (currentDir.charAt(1) == ':')
            currentDir = currentDir.substring(2);
        final String externalLocation = currentDir + "/public";
        Spark.staticFiles.externalLocation(externalLocation);
        Spark.staticFiles.location("/");
        Spark.staticFiles.expireTime(3L);
        Spark.port(port);
        log.trace("Spark Server init\n\tport: {}\n\tserving: {}", port, externalLocation);
        enableCORS("*", // origin
                "GET, PUT, POST, PATCH, DELETE, OPTIONS", // methods
                " Accept, Accept-Language, Content-Language, Content-Type"); // headers
        Spark.get(PATH_SUBJECTS, (req, res) -> {
            res.type("application/json");
            return api.getSubjects();
        });
        Spark.get(PATH_SCOPES, (req, res) -> {
            res.type("application/json");
            return api.getScopes();
        });
        Spark.get(PATH_PRIVILEGES, (req, res) -> {
            res.type("application/json");
            return api.getPrivileges();
        });
        Spark.get(PATH_ASSIGNMENTS, (req, res) -> {
            res.type("application/json");
            return api.getAssignments();
        });
    }

    // Enables CORS on requests. This method is an initialization method and should be called once.
    private void enableCORS(final String origin, final String methods, final String headers) {
        Spark.options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });
        Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            response.type("application/json");
        });
    }
}
