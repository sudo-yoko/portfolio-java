package com.example.application;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;

import com.example.domain.MethodContext;
import com.example.domain.Location;

public class LocationResolver {
    public static Location resolve(IType type) {
        Location location = new Location();
        location.setProjectName(type.getJavaProject().getElementName());
        location.setPackageName(type.getPackageFragment().getElementName());
        location.setClassName(type.getTypeQualifiedName('$'));
        return location;
    }

    public static Location resolve(IMethod method, String methodName, MethodContext context) {
        IType declaringType = method.getDeclaringType();
        if (declaringType == null) {
            throw new IllegalStateException("クラス情報の取得に失敗しました。");
        }
        Location location = new Location();
        location.setProjectName(method.getJavaProject().getElementName());
        location.setPackageName(declaringType.getPackageFragment().getElementName());
        location.setClassName(declaringType.getTypeQualifiedName('$'));
        location.setMethodName(methodName);
        location.setLineNumber(context.getLineNumber());
        return location;
    }
}