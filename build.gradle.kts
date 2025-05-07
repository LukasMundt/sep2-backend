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

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.postgresql:postgresql:42.7.5")
	implementation("org.jetbrains:annotations:26.0.2")
	implementation("jakarta.validation:jakarta.validation-api:3.1.1")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.h2database:h2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.modelmapper:modelmapper:3.2.3")

	//OpenApi
	api("org.springframework.boot:spring-boot-starter-web")
	api("org.springframework.data:spring-data-commons")
	api("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
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
