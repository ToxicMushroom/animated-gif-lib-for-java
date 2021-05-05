plugins {
    kotlin("jvm") version "1.5.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("maven-publish")
}

group = "me.melijn.gifdecoder"
version = "1.0.1"

repositories {
    mavenCentral()
    mavenLocal()
}


dependencies {

}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)}

tasks {
    withType(JavaCompile::class) {
        options.encoding = "UTF-8"
    }

    artifacts {
       archives(sourcesJar)
    }
}

publishing {
    repositories {
        maven {
            url = uri("https://nexus.melijn.com/repository/maven-releases/")
            credentials {
                username = property("melijnPublisher").toString()
                password = property("melijnPassword").toString()
            }
        }
    }
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}