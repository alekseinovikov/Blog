package me.alekseinovikov.blog.configuration.controller

import io.ktor.application.*
import io.ktor.routing.*
import me.alekseinovikov.blog.controller.Controller
import me.alekseinovikov.blog.controller.MainController
import org.kodein.di.Kodein
import org.kodein.di.generic.allInstances
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

fun registerControllers() = Kodein.Module(name = "controllers") {
    bind<Controller>() with provider { MainController(instance()) }
}

fun Application.configureControllers(kodein: Kodein) {
    val controllers: List<Controller> by kodein.allInstances()
    routing { controllers.forEach { it.register(this) } }
}
