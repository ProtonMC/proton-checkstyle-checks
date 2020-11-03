# Proton Checkstyle Checks
A repository containing some useful checkstyle checks (made for Proton).

## As of 2.0:
Checks:
* `io.github.protonmc.proton_checkstyle_checks.FromModuleCheck`
    * Enforces the use of a configurable annotation (by default `FromModule`) in Mixin injections.
    * Unstable? Kinda? It doesn't verify that the annotations are from the correct package.
* `io.github.protonmc.proton_checkstyle_checks.ModuleSuffixCheck`
    * Enforces subclasses of a configurable superclass (by default `ProtonModule`) to have a configurable suffix added to their name (by default `Module`).
    * Unstable? Kinda? It doesn't verify that the classes are from the correct package.


## 1.1 or earlier:
Checks:
* `io.github.protonmc.proton_checkstyle_checks.FromModuleCheck`
    * Enforces the use of `@FromModule` in Mixin injections.
    * Unstable? Kinda? It doesn't verify that the annotations are from the correct package.
