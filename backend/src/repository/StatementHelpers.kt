package me.alekseinovikov.blog.repository

import io.r2dbc.spi.*
import kotlinx.coroutines.*
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono

suspend inline fun <T : Closeable?, R> T.use(block: (T) -> R): R {
    try {
        return block(this)
    } finally {
        this?.close()?.awaitFirstOrNull()
    }
}

fun ConnectionPool.executeUpdateStatementBlocking(statement: String) = runBlocking {
    this@executeUpdateStatementBlocking.getConnection().use { connection ->
        runBlocking {
            connection.beginTransaction().awaitFirstOrNull()
            connection.createStatement(statement)
                .execute()
                .awaitFirst()
            connection.commitTransaction().awaitFirstOrNull()
        }
    }
}

suspend fun <T> ConnectionPool.queryList(statement: String, mapper: (Row, RowMetadata) -> T): List<T> = getConnection().use { connection ->
    connection.createStatement(statement)
        .execute()
        .toMono()
        .flatMapMany { res ->
            Flux.from(res.map(mapper))
        }.collectList()
        .awaitFirst()
}

suspend fun <T> ConnectionPool.querySingle(statement: String, mapper: (Row, RowMetadata) -> T): T? = getConnection().use { connection ->
    connection.createStatement(statement)
        .execute()
        .toMono()
        .awaitFirstOrNull()
        ?.map { row, meta -> mapper(row, meta) }
        ?.awaitFirstOrNull()
}

suspend fun <T> ConnectionPool.querySingle(statement: String, params: Map<String, Any>, mapper: (Row, RowMetadata) -> T): T? = getConnection().use { connection ->
    val parametrizedStatement = connection.createStatement(statement).also {
        params.forEach { (key, value) -> it.bind(key, value) }
    }
    parametrizedStatement.execute()
        .toMono()
        .awaitFirstOrNull()
        ?.map { row, meta -> mapper(row, meta) }
        ?.awaitFirstOrNull()
}
