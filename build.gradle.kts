plugins {
    java
    maven
    id("com.github.kt3k.coveralls") version "2.8.2"
}

group = "io.github.protonmc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.puppycrawl.tools", "checkstyle", "8.37")
}
