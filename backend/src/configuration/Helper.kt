package me.alekseinovikov.blog.configuration

import io.ktor.config.*

operator fun ApplicationConfig.get(property: String) = this.config(property).toString()
