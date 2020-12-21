package me.alekseinovikov.blog.controller

import io.ktor.application.*
import io.ktor.routing.*

interface Controller {
    fun Application.routing(): Routing
}
