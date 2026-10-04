package com.example.domain;

public class JavaLocationFormatter {
    public static String formatType(TypeLocation location) {
        return format(new String[] {
                location.getProjectName(),
                location.getPackageName(),
                location.getTypeName()
        });
    }

    public static String formatMethod(MethodLocation location) {
        return format(new String[] {
                location.getProjectName(),
                location.getPackageName(),
                location.getClassName(),
                String.format("L%d: %s", location.getLineNumber(), location.getMethodName())
        });
    }

    private static String format(String[] location) {
        return String.join("\n", location);
    }
}
