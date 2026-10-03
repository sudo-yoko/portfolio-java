package main.java.com.example.domain;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;

public class ClipTextBuilder {
    public static String build(ClipContext context) {
        String items[] = formatJavaLocation(context);
        return String.join("\n", items);
    }

    private static String[] formatJavaLocation(ClipContext context) {
        if (context.getElement() instanceof IType type) {
            return formatTypeLocation(type);
        }
        if (context.getElement() instanceof IMethod method) {
            return formatMethodLocation(method, context);
        }
        String elementName = (context.getElement() == null) ? "なし" : context.getElement().getElementName();
        throw new ClipException(String.format(
                "選択された要素（%s）は対象外です。クラス名またはメソッド名を選択してください。",
                elementName));
    }

    private static String[] formatTypeLocation(IType type) {
        return new String[] {
                // プロジェクト名
                type.getJavaProject().getElementName(),
                // パッケージ名
                type.getPackageFragment().getElementName(),
                // クラス名。内部クラスの場合は、親クラスを含めて$で区切って取得する
                type.getTypeQualifiedName('$')
        };
    }

    private static String[] formatMethodLocation(IMethod method, ClipContext context) {
        String methodName = method.getElementName();
        if (!context.getSelectedText().equals(methodName)) {
            throw new ClipException("メソッド名を選択してください。");
        }
        IType declaringType = method.getDeclaringType();
        if (declaringType == null) {
            throw new IllegalStateException("クラス情報の取得に失敗しました。");
        }
        return new String[] {
                // プロジェクト名
                method.getJavaProject().getElementName(),
                // パッケージ名
                declaringType.getPackageFragment().getElementName(),
                // クラス名。内部クラスの場合は、親クラスを含めて$で区切って取得する
                declaringType.getTypeQualifiedName('$'),
                // 行位置とメソッド名
                String.format("L%d: %s", context.getTextSelection().getStartLine() + 1, methodName)
        };
    }
}