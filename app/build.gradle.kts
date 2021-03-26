final var SEP = File.separator

final var javaFxVersion = "15.0.1"
final var fxmodules = listOf("base", "controls", "fxml", "media", "graphics")
final var platforms = listOf("win", "linux", "mac")

plugins {
    java
    application
    eclipse
    id("org.openjfx.javafxplugin") version "0.0.9" // https://github.com/openjfx/javafx-gradle-plugin
}

repositories {
    jcenter()
    mavenCentral()
}

java {                                      
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

javafx {
    version = javaFxVersion
    modules("javafx.controls", "javafx.fxml", "javafx.media")
}

dependencies {
    // Google Guava
    implementation("com.google.guava:guava:29.0-jre")

    // Apache Commons
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.apache.commons:commons-io:1.3.2")

    // Cross-platform JavaFX modules
    for (mod in fxmodules) {
        for (plat in platforms) {
            implementation("org.openjfx:javafx-${mod}:${javaFxVersion}:${plat}")
        }
    }

    // Jackson
    implementation("com.fasterxml.jackson:jackson-bom:2.12.2")

    // JUnit Jupiter
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    // TestFX for JavaFX testing
    testImplementation("org.testfx:testfx-core:4.0.16-alpha")
    testImplementation("org.testfx:testfx-junit5:4.0.16-alpha")

    // Hamcrest
    testImplementation("org.hamcrest:hamcrest:2.2")
}

application {
    mainClass.set("hotlinecesena.controller.core.Launcher")
}

tasks.test {
    useJUnitPlatform()
}
