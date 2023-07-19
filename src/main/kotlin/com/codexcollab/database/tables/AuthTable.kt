package com.codexcollab.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object AuthTable: Table("auth") {
    val id = integer("id").autoIncrement()
    val email = varchar("email",256)
    val password = text("password")
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(id)
}