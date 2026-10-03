package com.example.domain;

public class LocationFormatter {
    public static String[] asType(Location location) {
        return new String[] {
                location.getProjectName(),
                location.getPackageName(),
                location.getClassName()
        };
    }

    public static String[] asMethod(Location location) {
        return new String[] {
                location.getProjectName(),
                location.getPackageName(),
                location.getClassName(),
                String.format("L%d: %s", location.getLineNumber() + 1, location.getMethodName())
        };
    }

    public static String format(String[] location) {
        return String.join("\n", location);
    }
}
