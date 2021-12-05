import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
    kotlin("plugin.jpa") version "1.5.20"
}

group = "com.unisa"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/AlfredoSantoro/ODS")
            credentials {
                username = project.properties["repo_username"] as String
                password = project.properties["repo_password"] as String
            }
        }
        maven {
            name = "GitHub-Packages-Alfredo-Santoro"
            url = uri("https://maven.pkg.github.com/AlfredoSantoro/RDK")
            credentials {
                username = project.properties["repo_username"] as String
                password = project.properties["rdk_repo_password"] as String
            }
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security:2.5.4")
    implementation("com.auth0:java-jwt:3.18.1")
    implementation("org.springframework.boot:spring-boot-starter-web:2.5.4")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.5")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.4")
    implementation("com.ibm.db2.jcc:db2jcc:db2jcc4")
    runtimeOnly("com.ibm.db2.jcc:db2jcc:db2jcc4")
    implementation("commons-codec:commons-codec:1.11")
    implementation("org.springframework.boot:spring-boot-starter-freemarker:2.5.4")
    implementation("com.unisa:sesalabods:${project.properties["ods_version"]}")
    implementation("com.alfredosantoro:reservation-development-kit:${project.properties["rdk_version"]}")
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
