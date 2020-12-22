package me.alekseinovikov.blog.controller

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import me.alekseinovikov.blog.service.post.PostService

class PostController(private val postService: PostService) : Controller {
    override fun register(routing: Routing): Routing = with(routing) {
        route("/posts") {
            getById()
        }
        return@with this
    }

    private fun Route.getById() {
        get("/{id}") {
            val id = call.parameters["id"]?.toLong() ?: throw IllegalArgumentException("id")
            postService.getById(id)?.let { call.respond(it) } ?: call.respond(status = HttpStatusCode.NotFound, "Not found")
        }
    }

}
