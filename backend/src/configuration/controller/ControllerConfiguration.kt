package me.alekseinovikov.blog.configuration.controller

import io.ktor.application.*
import org.kodein.di.Kodein

fun Application.registerControllers() = Kodein.Module(name = "controllers") {

}
