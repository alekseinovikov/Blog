package me.alekseinovikov.blog.service

interface HealthService {
    suspend fun checkHealth(): Map<String, String>
    fun checkHealthBlocking(): Map<String, String>
}
