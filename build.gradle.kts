import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val flywayVersion by extra("7.3.1")
val jacksonVersion by extra("2.12.0")
val kotlinVersion by extra("1.4.21")
val kotlinCoroutinesVersion by extra("1.4.2-native-mt")
val postgresVersion by extra("42.2.18")
val postgresR2dbcVersion by extra("0.8.6.RELEASE")
val reactorKotlinVersion by extra("1.1.1")
val r2dbcPoolVersion by extra("0.8.5.RELEASE")
val reactorTestVersion by extra("3.4.1")
val springBootVersion by extra("2.4.0")
val testContainersVersion by extra("1.15.0")

plugins {
    id("org.springframework.boot") version "2.4.0"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
}

group = "com.ricardocosta"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:$reactorKotlinVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinCoroutinesVersion")
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

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
