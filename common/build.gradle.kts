plugins {
    id("java")
}
tasks.jar {
    manifest.attributes["Main-Class"] = "Main"
    val dependencies = configurations.runtimeClasspath.get().map(::zipTree) // OR .map { zipTree(it) }
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}


group = "org.example"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.json:json:20231013")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}