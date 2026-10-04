package com.example.domain;

public class MethodLocation {
    private final String projectName;
    private final String packageName;
    private final String typeName;
    private final String methodName;
    private final int lineNumber;

    public MethodLocation(
            String projectName, String packageName, String typeName, String methodName, int lineNumber) {
        this.projectName = projectName;
        this.packageName = packageName;
        this.typeName = typeName;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
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

    public String getMethodName() {
        return methodName;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
