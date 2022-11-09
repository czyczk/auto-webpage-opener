import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
    `java-library`
    `maven-publish`
}

group = "com.zenasoft"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

dependencies {
    val kotlinVersion = "1.7.21"
    val yamlVersion = "2.14.0"
    val janinoVersion = "3.1.8"
    val koinVersion = "3.2.2"

    testImplementation(kotlin("test"))

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.4")
    implementation("org.codehaus.janino:janino:${janinoVersion}")
    implementation("org.codehaus.janino:commons-compiler:${janinoVersion}")

    // Dependency injection
    implementation("io.insert-koin:koin-core:${koinVersion}")

    // Serde
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${yamlVersion}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${yamlVersion}")

    // Selenium
    implementation("org.seleniumhq.selenium:selenium-java:4.5.0")
    implementation("org.seleniumhq.selenium:selenium-edge-driver:4.5.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "auto-webpage-opener"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("Auto Webpage Opener")
                description.set("A simple fixture to help easily implement your own webpage opener.")
                url.set("https://www.github.com/czyczk/auto-webpage-opener")
//                properties.set(mapOf(
//                    "myProp" to "value",
//                    "prop.with.dots" to "anotherValue"
//                ))
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("czyczk")
                        name.set("Zenas Chen")
                        email.set("czyczk@qq.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://www.github.com/auto-webpage-opener.git")
                    developerConnection.set("scm:git:ssh://www.github.com/auto-webpage-opener.git")
                    url.set("https://www.github.com/czyczk/auto-webpage-opener/")
                }
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = uri(layout.buildDirectory.dir("repositories/releases"))
            val snapshotsRepoUrl = uri(layout.buildDirectory.dir("repositories/snapshots"))
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        }
    }
}