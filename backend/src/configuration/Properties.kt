package me.alekseinovikov.blog.configuration

import io.ktor.application.*
import io.ktor.config.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

fun Application.registerProperties() = Kodein.Module(name = "properties") {
    bind<DatabaseProperties>() with singleton { createDatabaseProperties(environment.config) }
}

data class DatabaseProperties(
        val host: String,
        val port: String,
        val database: String,
        val username: String,
        val password: String
)

private fun createDatabaseProperties(config: ApplicationConfig) =
        config.config("ktor").config("database").let {
            DatabaseProperties(
                    host = it["host"],
                    port = it["port"],
                    username = it["username"],
                    password = it["password"],
                    database = it["password"]
            )
        }
