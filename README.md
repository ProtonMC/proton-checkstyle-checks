# Proton Checkstyle Checks
A repository containing some useful checkstyle checks (made for Proton).

Checks:
* `io.github.protonmc.proton_checkstyle_checks.FromModuleCheck`
    * Enforces the use of `@FromModule` in Mixin injections.
    * Unstable? Kinda? It doesn't verify that the annotations are from the correct package.
