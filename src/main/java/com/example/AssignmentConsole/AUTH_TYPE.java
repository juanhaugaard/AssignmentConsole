package com.example.AssignmentConsole;

public enum AUTH_TYPE {
    subjects("/api/subjects","classpath:subjects.json"),
    privileges("/api/privileges","classpath:privileges.json"),
    roles("/api/roles","classpath:roles.json"),
    scopes("/api/scopes","classpath:scopes.json"),
    scopeTypes("/api/scopetypes","classpath:scopetypes.json"),
    assignments("/api/assignments","classpath:assignments.json");
    private String url;
    private String resource;
    AUTH_TYPE(String url, String resource) {
        this.url = url;
        this.resource = resource;
    }
    public String url() { return this.url; }
    public String resource() { return this.resource; }
}
