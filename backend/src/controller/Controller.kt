package me.alekseinovikov.blog.controller

import io.ktor.routing.*

interface Controller {
    fun register(routing: Routing): Routing
}
