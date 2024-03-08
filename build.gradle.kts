plugins {
	java
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.SoftwareprojektBackend"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.projectlombok:lombok:1.18.22")
	runtimeOnly("org.postgresql:postgresql")


	testImplementation("org.springframework.boot:spring-boot-starter-test")


	// Google Wallet library
	implementation(files ("libs/google-walletobjects-v1-rev_20230821-java.jar"))
	 implementation ("com.google.auth:google-auth-library-oauth2-http:1.19.0")
	 implementation ("com.google.apis:google-api-services-oauth2:v2-rev20200213-2.0.0")
	 implementation ("com.auth0:java-jwt:3.19.1")



}

tasks.withType<Test> {
	useJUnitPlatform()
}
