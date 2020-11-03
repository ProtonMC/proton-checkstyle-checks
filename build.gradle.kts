plugins {
    java
    maven
}

group = "io.github.protonmc"
version = "2.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.puppycrawl.tools", "checkstyle", "8.37")
}
