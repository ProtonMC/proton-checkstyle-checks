package io.github.protonmc.proton_checkstyle_checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * A check that forces all subclasses of a {@link #superclass configurable superclass} to
 * have a certain {@link #suffix configurable suffix} at the end of their name.
 * </p>
 *
 * <em>This check is not used in proton.</em>
 *
 * @author YTG1234
 * @since 2.0.0
 */
public class ModuleSuffixCheck extends AbstractCheck {
    /**
     * The superclass of which its subclasses must have {@link #suffix a suffix}.
     *
     * @since 2.0.0
     */
    private String superclass = "ProtonModule";
    /**
     * The suffix that subclasses of the {@link #superclass} must have.
     *
     * @since 2.0.0
     */
    private String suffix = "Module";

    /**
     * A setter for {@link #superclass}.
     *
     * @param superclass The new superclass value.
     *
     * @see #superclass
     */
    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }

    /**
     * A setter for {@link #suffix}
     *
     * @param suffix The new suffix.
     *
     * @see #suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * Returns the default token a check is interested in. Only used if the
     * configuration for a check does not define the tokens.
     *
     * @return the default tokens
     *
     * @see TokenTypes
     * @since 2.0.0
     */
    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.CLASS_DEF};
    }

    /**
     * Returns the default token a check is interested in. Only used if the
     * configuration for a check does not define the tokens.
     *
     * @return the default tokens
     *
     * @see TokenTypes
     * @since 2.0.0
     */
    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    /**
     * Returns the default token a check is interested in. Only used if the
     * configuration for a check does not define the tokens.
     *
     * @return the default tokens
     *
     * @see TokenTypes
     * @since 2.0.0
     */
    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    /**
     * Called for every {@link TokenTypes#CLASS_DEF} token.
     * <p>
     * For each token, looks for an extends clause that contains {@link #superclass},
     * and logs an error if the class name doesn't end with {@link #suffix}
     * </p>
     *
     * @param ast The token.
     *
     * @see #superclass
     * @see #suffix
     */
    @Override
    public void visitToken(DetailAST ast) {
        if (ast.findFirstToken(TokenTypes.EXTENDS_CLAUSE) == null) return;
        if (ast.findFirstToken(TokenTypes.EXTENDS_CLAUSE).findFirstToken(TokenTypes.IDENT).getText().contains(superclass) &&
            !ast.findFirstToken(TokenTypes.IDENT).getText().endsWith(suffix)) {
            log(ast.getLineNo(), "All subclasses of " + superclass + " need to have a " + suffix + " suffix!");
        }
    }
}
