package me.alekseinovikov.blog

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import me.alekseinovikov.blog.configuration.configureWeb
import me.alekseinovikov.blog.configuration.registerDatabase
import me.alekseinovikov.blog.configuration.registerProperties
import me.alekseinovikov.blog.repository.ConnectionPool
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.kodein.di.newInstance

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val propertiesModule = registerProperties()
    val databaseModule = registerDatabase()

    val kodein = Kodein {
        import(propertiesModule)
        import(databaseModule)
    }

    val pool: ConnectionPool by kodein.instance()

    configureWeb()

    routing {
        get("/") {
            val connection = pool.getConnection()
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}

