package com.example.domain;

public class MethodLocation {
    private final String projectName;
    private final String packageName;
    private final String className;
    private final String methodName;
    private final int lineNumber;

    public MethodLocation(
        String projectName, String packageName, String className, String methodName, int lineNumber) {
        this.projectName = projectName;
        this.packageName = packageName;
        this.className = className;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

}
