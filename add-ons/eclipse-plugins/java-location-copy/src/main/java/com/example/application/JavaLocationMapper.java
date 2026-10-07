package com.example.application;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;

import com.example.domain.MethodLocation;
import com.example.domain.TypeLocation;

/**
 * JDT要素をモデルに変換する
 */
public class JavaLocationMapper {
    public static TypeLocation map(IType type) {
        return new TypeLocation(
                type.getJavaProject().getElementName(),
                type.getPackageFragment().getElementName(),
                type.getTypeQualifiedName('$'));
    }

    public static MethodLocation map(IMethod method, int lineNumber) {
        IType declaringType = method.getDeclaringType();
        if (declaringType == null) {
            throw new IllegalStateException("型情報の取得に失敗しました。");
        }
        return new MethodLocation(
                method.getJavaProject().getElementName(),
                declaringType.getPackageFragment().getElementName(),
                declaringType.getTypeQualifiedName('$'),
                method.getElementName(),
                lineNumber);
    }
}