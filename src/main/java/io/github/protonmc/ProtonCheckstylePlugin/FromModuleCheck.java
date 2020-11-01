package io.github.protonmc.ProtonCheckstylePlugin;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class FromModuleCheck extends AbstractCheck {
    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        try {
            DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
            if (modifiers.getChildCount(TokenTypes.ANNOTATION) < 1) return;
            DetailAST modifier = modifiers.findFirstToken(TokenTypes.ANNOTATION);
            boolean foundFromModule = false;
            boolean foundMixinMethod = false;
            for (int i = 0; i < modifiers.getChildCount(TokenTypes.ANNOTATION); i++) {
                if (modifier.findFirstToken(TokenTypes.IDENT).getText().equals("Inject") ||
                    modifier.findFirstToken(TokenTypes.IDENT).getText().equals("Redirect") ||
                    modifier.findFirstToken(TokenTypes.IDENT).getText().equals("ModifyArg") ||
                    modifier.findFirstToken(TokenTypes.IDENT).getText().equals("ModifyArgs")) {
                    foundMixinMethod = true;
                    System.out.println("Found Mixin injection");
                } else if (modifier.findFirstToken(TokenTypes.IDENT).getText().equals("FromModule")) {
                    foundFromModule = true;
                    System.out.println("Found FromModule");
                }
                while(modifier.getType() != TokenTypes.ANNOTATION) {
                    modifier = modifier.getNextSibling();
                }
            }
            if (foundMixinMethod && !foundFromModule) {
                log(ast.getLineNo(), "All Mixin injections have to have a @FromModule annotation!");
            }
        } catch (Throwable t) {
            System.out.println(t.toString());
        }
    }
}
