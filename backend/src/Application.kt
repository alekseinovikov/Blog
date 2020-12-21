package me.alekseinovikov.blog

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import me.alekseinovikov.blog.configuration.configureWeb
import me.alekseinovikov.blog.configuration.database.migrateDatabase
import me.alekseinovikov.blog.configuration.registerDIDependencies
import me.alekseinovikov.blog.controller.Controller
import org.kodein.di.generic.allInstances

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val kodein = registerDIDependencies()
    migrateDatabase(kodein)
    configureWeb()

    val controllers: List<Controller> by kodein.allInstances<Controller>()
    controllers.forEach { controller -> controller.routing() }

    routing {
        get("/") {
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

