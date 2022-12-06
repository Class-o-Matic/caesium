import com.semanticversion.gradle.plugin.SemanticVersionGradlePlugin

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project
val koin_ktor: String by project

group = "io.classomatic"
version = "0.0.1"
apply<SemanticVersionGradlePlugin>()

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("com.dipien:semantic-version-gradle-plugin:1.3.1")
    }
}

plugins {
    application
    kotlin("jvm") version "1.7.21"
    id("io.ktor.plugin") version "2.1.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.21"
    id("com.dipien.semantic-version") version "1.3.1" apply false
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-config-yaml:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-cors-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-locations-jvm:2.1.3")
    implementation("io.ktor:ktor-client-core-jvm:2.1.3")
    implementation("io.ktor:ktor-client-apache-jvm:2.1.3")

    implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.8.0")

    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_ktor")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_ktor")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    testImplementation("io.insert-koin:koin-test:$koin_version")
}

var jarFileName = "$name-$version"

ktor {
    fatJar {
        jarFileName = (findProperty("jarFileName") ?: jarFileName) as String
        archiveFileName.set("${jarFileName}.jar")
    }
}

tasks.create("printJarName") {
    println("Jar file name: $jarFileName")
}
