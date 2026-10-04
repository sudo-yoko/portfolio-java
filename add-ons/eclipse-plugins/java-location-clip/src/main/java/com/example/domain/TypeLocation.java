package com.example.domain;

public class TypeLocation {
    private final String projectName;
    private final String packageName;
    private final String typeName;

    public TypeLocation(String projectName, String packageName, String typeName) {
        this.projectName = projectName;
        this.packageName = packageName;
        this.typeName = typeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getTypeName() {
        return typeName;
    }
}
