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
            getPreviews()
        }
        return@with this
    }

    private fun Route.getById() {
        get("/{id}") {
            respondOrNotFound {
                val id = getLongParam("id")
                postService.getById(id)
            }
        }
    }

    private fun Route.getPreviews() {
        get {
            respondOrNotFound {
                postService.getPreviews()
            }
        }
    }

}
