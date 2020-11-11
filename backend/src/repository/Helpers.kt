package me.alekseinovikov.blog.repository

import io.r2dbc.spi.Closeable
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.runBlocking

suspend fun <T : Closeable?, R> T.use(block: (T) -> R): R {
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
