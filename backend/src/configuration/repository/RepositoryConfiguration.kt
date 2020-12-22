package me.alekseinovikov.blog.configuration.repository

import me.alekseinovikov.blog.repository.PostRepository
import me.alekseinovikov.blog.repository.PostRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun registerRepositories() = Kodein.Module(name = "repositories") {
    bind<PostRepository>() with singleton { PostRepositoryImpl(instance()) }
}
