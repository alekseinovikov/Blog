package me.alekseinovikov.blog.configuration

import io.ktor.application.*
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import kotlinx.coroutines.reactive.awaitSingle
import me.alekseinovikov.blog.repository.ConnectionPool
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.time.Duration

fun Application.registerDatabase() = Kodein.Module(name = "database") {
    bind<ConnectionPool>() with singleton { createConnectionPool(instance()) }
}

private fun createConnectionPool(properties: DatabaseProperties): ConnectionPool {
    val connectionFactory = createConnectionFactory(properties)
    val pool = io.r2dbc.pool.ConnectionPool(
            ConnectionPoolConfiguration.builder(connectionFactory)
                    .maxIdleTime(Duration.ofSeconds(properties.maxIdleSeconds))
                    .maxSize(properties.poolSize)
                    .build()
    )
    return object : ConnectionPool {
        override suspend fun getConnection() = pool.create().awaitSingle()
    }
}

private fun createConnectionFactory(properties: DatabaseProperties): ConnectionFactory = PostgresqlConnectionFactory(
        PostgresqlConnectionConfiguration.builder()
                .host(properties.host)
                .port(properties.port.toInt())
                .username(properties.username)
                .password(properties.password)
                .database(properties.database)
                .build()
)
