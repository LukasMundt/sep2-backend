plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.sonarqube") version "4.4.1.3373"

	//OpenApi
	`java-library`
	`maven-publish`
}

group = "de.haw.se2.praktikum.speedrun"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val apacheCommonsEmailVersion = "1.9.0"
var postgresqlVersion = "42.7.5"
var jetbrainsAnnotationsVersion = "26.0.2"
val modelMapperVersion = "3.2.3"
val jsonWebTokenVersion = "0.11.5"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("commons-validator:commons-validator:$apacheCommonsEmailVersion")
	implementation("org.postgresql:postgresql:$postgresqlVersion")
	implementation("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")
	implementation("org.modelmapper:modelmapper:$modelMapperVersion")
	implementation("io.jsonwebtoken:jjwt-api:$jsonWebTokenVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:$jsonWebTokenVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jsonWebTokenVersion")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.h2database:h2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	//OpenApi
	api("org.springframework.boot:spring-boot-starter-web")
	api("org.springframework.data:spring-data-commons")
	api("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")
	api("com.google.code.findbugs:jsr305:3.0.2")
	api("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
	api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	api("org.openapitools:jackson-databind-nullable:0.2.6")
	api("org.springframework.boot:spring-boot-starter-validation")
	api("com.fasterxml.jackson.core:jackson-databind")
	//testImplementation("org.springframework.boot:spring.boot.starter.test")
}

sonar {
	properties {
		property("sonar.projectKey", "se2-backend")
		property("sonar.projectName", "se2-backend")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
