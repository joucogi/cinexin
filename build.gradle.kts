import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    application
    id("java")
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.adarshr.test-logger") version "3.2.0"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
}

buildscript {
    repositories {
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("com.adarshr:gradle-test-logger-plugin:3.2.0")
    }
}

sourceSets {
    main {
        java.srcDirs("apps/main/kotlin")
        resources.srcDirs("apps/main/resources")
    }

    test {
        java.srcDirs("apps/test/kotlin")
        resources.srcDirs("apps/test/resources")
    }
}

allprojects {
    repositories {
        mavenCentral()
    }

    apply {
        plugin("java")
        plugin("com.adarshr.test-logger")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("io.spring.dependency-management")
        plugin("org.springframework.boot")
    }

    version = "0.0.1"
    java.sourceCompatibility = JavaVersion.VERSION_11
    java.targetCompatibility = JavaVersion.VERSION_11

    val junitVersion = "5.8.2"
    val cucumberVersion = "7.2.3"

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
        testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")
        testImplementation("io.cucumber:cucumber-spring:$cucumberVersion")
        testImplementation("org.junit.vintage:junit-vintage-engine:$junitVersion")
    }

    tasks.withType<Test> {
        useJUnitPlatform()

        testLogging {
            events = mutableSetOf(
                PASSED,
                FAILED,
                SKIPPED
            )
        }
    }
}

// All subprojects
subprojects {
    group = "com.${rootProject.name}.${project.name}"

    sourceSets {
        main {
            java.srcDirs("main/kotlin")
            resources.srcDirs("main/resources")
        }

        test {
            java.srcDirs("test/kotlin")
            resources.srcDirs("test/resources")
        }
    }

    dependencies {
        testImplementation(rootProject.sourceSets["main"].output)

        if (project.name != "shared") {
            implementation(project(":shared"))

            testImplementation(project(":shared").dependencyProject.sourceSets["test"].output)
        }
    }

    tasks.getByName<BootJar>("bootJar") {
        enabled = false
    }

    tasks.getByName<Jar>("jar") {
        enabled = true
    }

    tasks.withType<JavaCompile> {
        enabled = false
    }
}

val cucumberRuntime: Configuration by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

val subprojectArg: String by project
task("featureTest") {
    dependsOn(tasks.assemble, tasks.testClasses)
    doLast {
        javaexec {
            mainClass.set("io.cucumber.core.cli.Main")
            classpath = cucumberRuntime + sourceSets.main.get().output + sourceSets.test.get().output
            args = listOf(
                "--plugin", "pretty",
                "--glue", "com.cinexin.apps.${subprojectArg}.infrastructure.cucumber",
                "apps/test/resources/${subprojectArg}/features"
            )
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

application {
    mainClass.set("com.cinexin.apps.StarterKt")
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":backoffice"))
    implementation(project(":visualizer"))

    testImplementation(project(":shared").dependencyProject.sourceSets["test"].output)
}