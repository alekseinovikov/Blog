package me.alekseinovikov.blog.configuration.controller

import io.ktor.application.*
import io.ktor.routing.*
import me.alekseinovikov.blog.controller.Controller
import me.alekseinovikov.blog.controller.MainController
import me.alekseinovikov.blog.controller.PostController
import org.kodein.di.Kodein
import org.kodein.di.bindings.subTypes
import org.kodein.di.generic.*

fun registerControllers() = Kodein.Module(name = "controllers") {
    bind() from singleton { MainController(instance()) }
    bind() from singleton { PostController(instance()) }
}

fun Application.configureControllers(kodein: Kodein) {
    val controllers: List<Controller> by kodein.allInstances()
    routing { controllers.forEach { it.register(this) } }
}
