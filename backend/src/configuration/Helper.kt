package me.alekseinovikov.blog.configuration

import io.ktor.config.*

operator fun ApplicationConfig.get(property: String) = this.property(property).getString()
