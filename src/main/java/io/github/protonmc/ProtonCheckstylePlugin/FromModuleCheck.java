package io.github.protonmc.ProtonCheckstylePlugin;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class FromModuleCheck extends AbstractCheck {
    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF};
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
        DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS).findFirstToken(TokenTypes.ANNOTATION).findFirstToken(TokenTypes.IDENT);
        if(modifiers.getText().equals("Mixin")) {
            String message = "Mixin class found";
            log(ast.getLineNo(), message);
        }
    }
}
