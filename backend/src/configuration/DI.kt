package me.alekseinovikov.blog.configuration

import io.ktor.application.*
import me.alekseinovikov.blog.configuration.database.registerDatabase
import org.kodein.di.Kodein

fun Application.registerDIDependencies() = Kodein {
    import(registerProperties(environment.config))
    import(registerDatabase())
}
