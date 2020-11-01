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
        if (ast == null) return;
        if (ast.getChildCount(TokenTypes.MODIFIERS) < 1) return;
        if (ast.findFirstToken(TokenTypes.MODIFIERS) == null) return;
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
            } else if (modifier.findFirstToken(TokenTypes.IDENT).getText().equals("FromModule")) {
                foundFromModule = true;
            }
            while(modifier.getType() != TokenTypes.ANNOTATION) {
                modifier = modifier.getNextSibling();
            }
        }
        if (foundMixinMethod && !foundFromModule) {
            log(ast.getLineNo(), "All Mixin injections have to have a @FromModule annotation!");
        }
    }
}
