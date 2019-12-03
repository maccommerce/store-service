import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.3.61"
}

group = "br.com.maccommerce"
version = "1.0.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

application {
    mainClassName = "br.com.maccommerce.storeservice.app.AppKt"
}

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to application.mainClassName
            )
        )
    }

    from(configurations.runtimeClasspath.map { configuration ->
        configuration.asFileTree.map {
            if(it.isDirectory) it else zipTree(it)
        }
    })

    archiveFileName.set(project.name)

    // removing signed files from jar
    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
}

tasks.test {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.http4k:http4k-core:3.200.0")
    implementation("org.http4k:http4k-server-jetty:3.200.0")
    implementation("org.http4k:http4k-format-jackson:3.200.0")
    implementation("org.jetbrains.exposed:exposed:0.17.7")
    implementation("org.koin:koin-core:2.0.1")
    implementation("com.zaxxer:HikariCP:3.4.1")
    implementation("org.slf4j:slf4j-simple:1.7.29")
    implementation("org.postgresql:postgresql:42.2.8")

    testImplementation("org.jetbrains.kotlin:kotlin-test:1.3.61")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.8")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:2.0.8")
}
