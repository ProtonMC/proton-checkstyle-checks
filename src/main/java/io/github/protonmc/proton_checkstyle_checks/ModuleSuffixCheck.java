package io.github.protonmc.proton_checkstyle_checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ModuleSuffixCheck extends AbstractCheck {
    private String superclass = "ProtonModule";
    private String suffix = "Module";

    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.CLASS_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.findFirstToken(TokenTypes.EXTENDS_CLAUSE) == null) return;
        if (ast.findFirstToken(TokenTypes.EXTENDS_CLAUSE).findFirstToken(TokenTypes.IDENT).getText().contains(superclass) &&
            !ast.findFirstToken(TokenTypes.IDENT).getText().endsWith(suffix)) {
            log(ast.getLineNo(), "All subclasses of " + superclass + " need to have a " + suffix + " suffix!");
        }
    }
}
