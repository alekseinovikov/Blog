import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val kodein_version: String by project
val reactor_kotlin_extention_version: String by project
val r2dbc_postgres_version: String by project
val r2dbc_pool_version: String by project
val coroutines_reactor_version: String by project
val jackson_version: String by project

plugins {
    application
    kotlin("jvm") version "1.4.21"
}

group = "me.alekseinovikov.blog"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-host-common:$ktor_version")
    implementation("io.ktor:ktor-jackson:$ktor_version")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jackson_version")
    implementation("org.kodein.di:kodein-di-generic-jvm:$kodein_version")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:$reactor_kotlin_extention_version")
    implementation("io.r2dbc:r2dbc-postgresql:$r2dbc_postgres_version")
    implementation("io.r2dbc:r2dbc-pool:$r2dbc_pool_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutines_reactor_version")

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

tasks.withType<KotlinCompile>().configureEach {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()

    kotlinOptions.jvmTarget = "1.8"
}

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")
