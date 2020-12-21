package me.alekseinovikov.blog.repository

import io.r2dbc.spi.Closeable
import io.r2dbc.spi.Result
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.coroutines.*
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
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
    val eventList: List<Result> = connection.createStatement(statement)
        .execute()
        .toFlux()
        .collectList()
        .awaitFirst()

    eventList
        .map { it.map { row, meta -> mapper(row, meta) }.awaitFirst() }
}

suspend fun <T> ConnectionPool.querySingle(statement: String, mapper: (Row, RowMetadata) -> T): T = getConnection().use { connection ->
    val event: Result = connection.createStatement(statement)
        .execute()
        .toMono()
        .awaitFirst()

    event.map { row, meta -> mapper(row, meta) }.awaitFirst()
}
