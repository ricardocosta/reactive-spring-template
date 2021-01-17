import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val flywayVersion by extra("7.3.1")
val jacksonVersion by extra("2.12.0")
val kotlinVersion by extra("1.4.21")
val kotlinCoroutinesVersion by extra("1.4.2-native-mt")
val kotlinLoggingVersion by extra("2.0.4")
val postgresVersion by extra("42.2.18")
val postgresR2dbcVersion by extra("0.8.6.RELEASE")
val reactorKotlinVersion by extra("1.1.1")
val r2dbcPoolVersion by extra("0.8.5.RELEASE")
val reactorTestVersion by extra("3.4.1")
val springBootVersion by extra("2.4.0")
val springDocVersion by extra("1.5.2")
val testContainersVersion by extra("1.15.1")

plugins {
    id("com.geoffgranum.gradle-conventional-changelog") version "0.3.1"
    id("com.github.ben-manes.versions") version "0.36.0"
    id("com.star-zero.gradle.githook") version "1.2.1"
    id("io.gitlab.arturbosch.detekt") version "1.15.0-RC1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    id("org.springframework.boot") version "2.4.0"
    id("ru.netris.commitlint") version "1.4"
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
    jacoco
}

group = "com.ricardocosta"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    jcenter {
        content {
            // just allow to include kotlinx projects
            // detekt needs 'kotlinx-html' for the html report
            includeGroup("org.jetbrains.kotlinx")
        }
    }
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:$reactorKotlinVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinCoroutinesVersion")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-kotlin:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-data-rest:$springDocVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-webflux:$springBootVersion")
    implementation(platform("org.testcontainers:testcontainers-bom:$testContainersVersion"))

    developmentOnly("org.springframework.boot:spring-boot-devtools:$springBootVersion")

    runtimeOnly("io.r2dbc:r2dbc-postgresql:$postgresR2dbcVersion")
    runtimeOnly("org.postgresql:postgresql:$postgresVersion")

    testImplementation("io.projectreactor:reactor-test:$reactorTestVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
    testImplementation("org.testcontainers:postgresql:$testContainersVersion")
    testImplementation("org.testcontainers:r2dbc:$testContainersVersion")
}

changelog {
    appName = "Template Application"
    repoUrl = "https://github.com/ricardocosta/reactive-spring-template"
    match = "^fix|^feat|^chore|^perf|^refactor|BREAKING"
}

detekt {
    toolVersion = "1.15.0-RC1"
    parallel = true
}

githook {
    failOnMissingHooksDir = true
    createHooksDirIfNotExist = false
    hooks {
        register("commit-msg") {
            task = "commitlint"
        }

        register("pre-commit") {
            task = "ktlintCheck detekt"
        }
    }
}

jacoco {
    toolVersion = "0.8.6"
    reportsDir = file("$rootDir/coverage")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named("dependencyUpdates", DependencyUpdatesTask::class.java).configure {
    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }

    rejectVersionIf {
        isNonStable(candidate.version)
    }

    checkForGradleUpdate = true
    gradleReleaseChannel = "current"
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.isEnabled = true
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.85".toBigDecimal()
            }
        }
    }
}
