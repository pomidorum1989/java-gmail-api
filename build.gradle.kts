plugins {
    id("java")
}

group = "io.dorum"
version = "1.0-SNAPSHOT"

val junitBomVersion = "5.10.0"
val googleApiClientVersion = "2.6.0"
val googleOauthClientVersion = "1.36.0"
val googleApiServicesGmailVersion = "v1-rev20240520-2.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:$junitBomVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.api-client:google-api-client:$googleApiClientVersion")
    implementation("com.google.oauth-client:google-oauth-client:$googleOauthClientVersion")
    implementation("com.google.apis:google-api-services-gmail:$googleApiServicesGmailVersion")
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        showStandardStreams = true
    }

    filter {
        includeTestsMatching("io.dorum.tests.GmailTest.*")
    }
}