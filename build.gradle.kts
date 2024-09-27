plugins {
    id("java")
    alias(libs.plugins.lombok)
    `maven-publish`
}

val majorVersion = "0.1"

group = "io.github.singlerr.semaphore"
version = "${majorVersion}-${System.getenv()["BUILD_NUMBER"] ?: "999"}"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(libs.jgltf.model)
    implementation(libs.jetbrains.annotations)
}

tasks.test {
    useJUnitPlatform()
}


publishing {
    repositories {
        maven {
            name = "githubRepo"
            url = uri(project.findProperty("localMvnRepo").toString())
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            groupId = group.toString()
            artifactId = project.name
            setVersion(project.version)
            from(components["java"])
        }
    }
}