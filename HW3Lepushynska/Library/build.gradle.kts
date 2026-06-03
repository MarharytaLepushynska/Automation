plugins {
    id("java")
}

apply<ReportPlugin>()

group = "org.naukma"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    dependsOn("createBookList")
}

tasks.register("createBookList") {
    group = "library"
    description = "Creates a new library book list"

    doLast {
        val file = file("bookList.txt")
        file.writeText("""
            Acotar
            Dune
            Harry Potter
            Throne of Glass
            Love, Theoretically
        """.trimIndent())
        logger.lifecycle("Added test file")
    }

}
