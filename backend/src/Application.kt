package me.alekseinovikov.blog

import io.ktor.application.*
import me.alekseinovikov.blog.configuration.configureWeb
import me.alekseinovikov.blog.configuration.controller.configureControllers
import me.alekseinovikov.blog.configuration.database.migrateDatabase
import me.alekseinovikov.blog.configuration.registerDIDependencies

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val kodein = registerDIDependencies()
    migrateDatabase(kodein)
    configureWeb()
    configureControllers(kodein)
}

