plugins {
	java
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "1.8.20"
	id("jacoco")
}

group = "com.myretailer.services"
version = "1.0.0-SNAPSHOT"
description = "Restfull service for the retailer application"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

repositories {
	mavenCentral()
	mavenLocal()
	maven(url = "https://repo.spring.io/release")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.h2database:h2:2.2.224")
	implementation("io.projectreactor:reactor-core:3.4.14")
	implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
	implementation("org.projectlombok:lombok:1.18.22")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.jetbrains:annotations:24.1.0")
	implementation("com.fasterxml.jackson.core:jackson-databind")
	annotationProcessor("org.projectlombok:lombok:1.18.22")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
	testImplementation(kotlin("test"))
}

tasks.test {
	useJUnitPlatform()
	finalizedBy("jacocoTestReport") // Generate the report after tests run
}

tasks.named<JacocoReport>("jacocoTestReport") {
	dependsOn(tasks.test) // Tests should run before generating the report

	reports {
		xml.required.set(true)
		html.required.set(true)
		html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
	}
}