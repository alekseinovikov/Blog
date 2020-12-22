package me.alekseinovikov.blog.configuration.service

import me.alekseinovikov.blog.service.health.HealthService
import me.alekseinovikov.blog.service.health.HealthServiceImpl
import me.alekseinovikov.blog.service.post.PostService
import me.alekseinovikov.blog.service.post.PostServiceImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun registerServices() = Kodein.Module(name = "services") {
    bind<HealthService>() with singleton { HealthServiceImpl(instance()) }
    bind<PostService>() with singleton { PostServiceImpl(instance()) }
}
