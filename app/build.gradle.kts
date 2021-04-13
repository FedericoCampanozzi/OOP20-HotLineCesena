val javaFxVersion = "15.0.1"
val fxmodules = listOf("base", "controls", "fxml", "media", "graphics", "swing")
val platforms = listOf("win", "linux", "mac")

plugins {
    java
    application
    eclipse
    id("org.openjfx.javafxplugin") version "0.0.9" // https://github.com/openjfx/javafx-gradle-plugin

    /*
     * Adds tasks to export a runnable jar.
     * In order to create it, launch the "shadowJar" task.
     * The runnable jar will be found in build/libs/projectname-all.jar
     */
    id("com.github.johnrengelman.shadow") version "6.1.0"
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
    modules("javafx.base", "javafx.controls", "javafx.fxml", "javafx.media", "javafx.graphics", "javafx.swing")
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
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.8")

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
    
    /*
     * mainClassName was deprecated by Gradle, but it is still required by John Engelman's Shadow plugin.
     * A pull request with a fix was already merged, but it hasn't been released yet;
     * see https://github.com/johnrengelman/shadow/issues/609 and https://github.com/johnrengelman/shadow/pull/612
     */
    @Suppress("DEPRECATION")
    mainClassName = mainClass.get()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    // Enables JUnit 5 Jupiter module
    useJUnitPlatform()
}
