package io.github.protonmc.proton_checkstyle_checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * A check that verifies that all Mixin methods have a configurable annotation (by default {@code FromModule}).
 *
 * @author YTG1234
 * @since 1.0.0
 */
public class FromModuleCheck extends AbstractCheck {
    /**
     * The configurable annotation.
     *
     * @since 2.0.0
     */
    private String annotation = "FromModule";

    /**
     * A setter method for {@link #annotation}.
     *
     * @param annotation The required annotation for Mixin injections.
     *
     * @see #annotation
     * @since 2.0.0
     */
    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    /**
     * Returns the default token a check is interested in. Only used if the
     * configuration for a check does not define the tokens.
     *
     * @return the default tokens
     *
     * @see TokenTypes
     * @since 1.0.0
     */
    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    /**
     * The configurable token set.
     * Used to protect Checks against malicious users who specify an
     * unacceptable token set in the configuration file.
     * The default implementation returns the check's default tokens.
     *
     * @return the token set this check is designed for.
     *
     * @see TokenTypes
     * @since 1.0.0
     */
    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    /**
     * The tokens that this check must be registered for.
     *
     * @return the token set this must be registered for.
     *
     * @see TokenTypes
     * @since 1.0.0
     */
    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    /**
     * Visits all {@link TokenTypes#METHOD_DEF} tokens in the file structure.
     * <p>
     * For each token, looks for annotation tokens. If one (or more) Mixin injection annotation is present,
     * the {@link FromModuleCheck#annotation required annotation} is required on the method.
     * </p>
     *
     * @param ast The token to visit.
     *
     * @since 1.0.0
     */
    @Override
    public void visitToken(DetailAST ast) {
        try {
            DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
            if (modifiers.getChildCount(TokenTypes.ANNOTATION) < 1) return;
            DetailAST modifier = modifiers.findFirstToken(TokenTypes.ANNOTATION);
            boolean foundFromModule = false;
            boolean foundMixinMethod = false;
            while(modifier != null) {
                if (modifier.getType() != TokenTypes.ANNOTATION) break;
                if (modifier.findFirstToken(TokenTypes.IDENT).getText().contains("Inject") ||
                    modifier.findFirstToken(TokenTypes.IDENT).getText().contains("Redirect") ||
                    modifier.findFirstToken(TokenTypes.IDENT).getText().contains("ModifyArg") ||
                    modifier.findFirstToken(TokenTypes.IDENT).getText().contains("ModifyConstant") ||
                    modifier.findFirstToken(TokenTypes.IDENT).getText().contains("ModifyVariable") ||
                    modifier.findFirstToken(TokenTypes.IDENT).getText().contains("Overwrite") ||
                    modifier.findFirstToken(TokenTypes.IDENT).getText().contains("Unique")) {
                    foundMixinMethod = true;
                }
                if (modifier.findFirstToken(TokenTypes.IDENT).getText().contains(annotation)) {
                    foundFromModule = true;
                }
                modifier = modifier.getNextSibling();
            }
            if (foundMixinMethod && !foundFromModule) {
                log(modifier.getLineNo(), "All Mixin injections must have a @" + annotation + " annotation!");
            }
        } catch (Throwable t) {
            System.out.println(t.toString());
        }
    }
}
