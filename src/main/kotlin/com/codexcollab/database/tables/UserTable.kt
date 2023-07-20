package com.codexcollab.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object UserTable: Table("users") {
    val id = integer("id").autoIncrement()
    val email = varchar("email",256)
    val name = varchar("name",256)
    val designation = varchar("designation", 256)
    val authToken = text("auth_token")
    val lastUpdatedAt = datetime("last_updated_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(email)
}