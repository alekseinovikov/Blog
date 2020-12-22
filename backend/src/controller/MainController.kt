package me.alekseinovikov.blog.controller

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.utils.io.*
import me.alekseinovikov.blog.service.health.HealthService

class MainController(private val healthService: HealthService) : Controller {

    override fun register(routing: Routing): Routing = with(routing) {
        root()
        healthCheck()
        healthCheckBlocking()
        static()

        return@with this
    }

    private fun Routing.root() {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }
    }

    private fun Routing.healthCheck() {
        get("/health") {
            call.respond(healthService.checkHealth())
        }
    }

    private fun Routing.healthCheckBlocking() {
        get("/health-blocking") {
            call.respond(healthService.checkHealthBlocking())
        }
    }

    private fun Routing.static() {
        static("/static") {
            resources("static")
        }
    }

}
