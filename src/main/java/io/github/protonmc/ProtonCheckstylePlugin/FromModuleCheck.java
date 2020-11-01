package io.github.protonmc.ProtonCheckstylePlugin;

import com.puppycrawl.tools.checkstyle.api.*;

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
            for (int i = 0; i < modifier.getChildCount(); i++) {
                if (modifier.findFirstToken(TokenTypes.IDENT).getText().equals("Inject") ||
                    modifier.findFirstToken(TokenTypes.IDENT).getText().equals("Redirect") ||
                    modifier.findFirstToken(TokenTypes.IDENT).getText().equals("ModifyArg") ||
                    modifier.findFirstToken(TokenTypes.IDENT).getText().equals("ModifyArgs")) {
                    foundMixinMethod = true;
                }
                if (modifier.findFirstToken(TokenTypes.IDENT).getText().equals("FromModule")) {
                    foundFromModule = true;
                }
                modifier = modifier.getNextSibling();
            }
            if (foundMixinMethod && !foundFromModule) {
                log(modifier.getLineNo(), "All Mixin injections have to have a @FromModule annotation!");
            }
        } catch (Throwable t) {
            System.out.println(t.toString());
        }
    }
}
