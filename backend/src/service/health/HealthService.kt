package me.alekseinovikov.blog.service.health

interface HealthService {
    suspend fun checkHealth(): Map<String, String>
    fun checkHealthBlocking(): Map<String, String>
}
