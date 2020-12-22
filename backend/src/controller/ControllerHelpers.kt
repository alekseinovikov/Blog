package me.alekseinovikov.blog.controller

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

suspend inline fun PipelineContext<*, ApplicationCall>.respondOrNotFound(provider: () -> Any?) = provider()?.let { call.respond(it) }
    ?: call.respond(status = HttpStatusCode.NotFound, "Not found")

fun PipelineContext<*, ApplicationCall>.getLongParam(name: String) = call.parameters[name]?.toLong()
    ?: throw IllegalArgumentException("Parameter $name must be present!")
