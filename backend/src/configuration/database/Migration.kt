package me.alekseinovikov.blog.configuration.database

import io.ktor.application.*
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.runBlocking
import me.alekseinovikov.blog.repository.ConnectionPool
import me.alekseinovikov.blog.repository.executeUpdateStatementBlocking
import me.alekseinovikov.blog.repository.use
import org.intellij.lang.annotations.Language
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import reactor.kotlin.core.publisher.toMono
import java.io.File

fun Application.migrateDatabase(kodein: Kodein) {
    val connectionPool: ConnectionPool by kodein.instance()
    prepareMigrationStructures(connectionPool)

    val ranMigrations = findAllRunMigrationNumbers(connectionPool)
    findMigrationFiles(environment.classLoader)
            .filter { ranMigrations.contains(it.getMigrationNumber()).not() }
            .sortedBy { it.name }
            .forEach { migrate(it, connectionPool) }
}

private fun prepareMigrationStructures(connectionPool: ConnectionPool) {
    val sql = """
        CREATE TABLE IF NOT EXISTS migrations(
            id BIGINT NOT NULL PRIMARY KEY,
            run_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
        );
    """.trimIndent()

    connectionPool.executeUpdateStatementBlocking(sql)
}

private fun findAllRunMigrationNumbers(connectionPool: ConnectionPool): Set<String> {
    @Language("PostgreSQL")
    val sql = """
                SELECT id FROM migrations ORDER BY id;
            """.trimIndent()

    return runBlocking {
        connectionPool.getConnection().use { con ->
            runBlocking {
                con.createStatement(sql)
                        .execute()
                        .toMono()
                        .flatMapMany { it.map { row, _ -> row.get("id", java.lang.Long::class.java) } }
                        .map { it.toString() }
                        .collectList()
                        .awaitFirst()
            }
        }
    }.toSet()
}

private fun findMigrationFiles(classLoader: ClassLoader): List<File> {
    val folder = classLoader.getResource("migrations")!!
    val folderFile = File(folder.toURI())

    if (folderFile.exists().not() || folderFile.isDirectory.not()) {
        throw IllegalStateException("Can't find `migrations` folder in resources!")
    }

    return folderFile.listFiles()!!.toList()
}

private fun File.getMigrationNumber() = this.name.replace(".sql", "")

private fun migrate(file: File, connectionPool: ConnectionPool) {
    val sql = file.readLines().joinToString(separator = "\n") + generateRegisterMigrationSql(file)
    connectionPool.executeUpdateStatementBlocking(sql)
}

private fun generateRegisterMigrationSql(file: File): String =
        file.getMigrationNumber().let { "; INSERT INTO migrations(id) VALUES($it);" }
