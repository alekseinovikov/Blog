package me.alekseinovikov.blog.repository

import io.r2dbc.spi.Connection

interface ConnectionPool {
    suspend fun getConnection(): Connection
}
