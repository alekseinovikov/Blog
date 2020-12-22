package me.alekseinovikov.blog.service.health

import kotlinx.coroutines.runBlocking
import me.alekseinovikov.blog.repository.ConnectionPool
import me.alekseinovikov.blog.repository.querySingle
import me.alekseinovikov.blog.service.HealthService

class HealthServiceImpl(private val connectionPool: ConnectionPool) : HealthService {

    override suspend fun checkHealth(): Map<String, String> =
        mapOf(
            checkService(),
            checkDB()
        )

    override fun checkHealthBlocking(): Map<String, String> = runBlocking {
        mapOf(
            checkService(),
            checkDB()
        )
    }

    private fun checkService() = "SERVICE" to "OK"
    private suspend fun checkDB() = try {
        val result = connectionPool.querySingle("SELECT 1;") { row, _ -> row.get(0, java.lang.Long::class.java) }
        assert(result!!.toLong() == 1L)
        "DB" to "OK"
    } catch (ex: Exception) {
        "DB" to (ex.message ?: "Connection error")
    }

}
