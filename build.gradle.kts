val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.9.0"
    id("io.ktor.plugin") version "2.3.2"
}

group = "com.codexcollab"
version = "0.0.1"
application {
    mainClass.set("com.codexcollab.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

tasks {
    create("stage").dependsOn("installDist")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-jackson:1.6.8")
    implementation("io.ktor:ktor-auth:1.6.8")
    implementation("io.ktor:ktor-auth-jwt:1.6.8")
    implementation("commons-codec:commons-codec:1.15")
    implementation("org.jetbrains.exposed:exposed-core:0.37.3")
    implementation("org.jetbrains.exposed:exposed-dao:0.37.3")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.37.3")
    implementation("org.jetbrains.exposed:exposed-java-time:0.37.3")
    implementation("org.postgresql:postgresql:42.3.3")
    implementation("com.zaxxer:HikariCP:5.0.1")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}