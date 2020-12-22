package me.alekseinovikov.blog.repository

import io.r2dbc.spi.Row
import java.time.ZonedDateTime

fun Row.getNullableLong(columnName: String): Long? = this.get(columnName, java.lang.Long::class.java)?.toLong()
fun Row.getLong(columnName: String): Long = this.get(columnName, java.lang.Long::class.java)!!.toLong()

fun Row.getNullableString(columnName: String): String? = this.get(columnName, java.lang.String::class.java)?.toString()
fun Row.getString(columnName: String): String = this.get(columnName, java.lang.String::class.java)!!.toString()

fun Row.getNullableZonedDateTime(columnName: String): ZonedDateTime? = this.get(columnName, ZonedDateTime::class.java)
fun Row.getZonedDateTime(columnName: String): ZonedDateTime = this.get(columnName, ZonedDateTime::class.java)!!
