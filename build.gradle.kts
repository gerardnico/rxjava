plugins {
    // Apply the java-library plugin to add support for Java Library
    `java-library`
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api("io.reactivex.rxjava3:rxjava:3.0.1")

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    // implementation("io.reactivex.rxjava2:rxjava:2.2.0")

    // Use JUnit test framework
    testImplementation("junit:junit:4.12")
    testImplementation("io.reactivex.rxjava3:rxjava:3.0.1")
}
