package com.codexcollab.repo.user.usecase

import com.codexcollab.database.connection.DatabaseFactory.dbQuery
import com.codexcollab.database.tables.UserTable
import com.codexcollab.model.request.RegisterUser
import com.codexcollab.model.response.User
import com.codexcollab.repo.user.gateway.UserUseCaseImpl
import org.jetbrains.exposed.sql.*
import java.time.LocalDateTime

class UserUseCase : UserUseCaseImpl {
    override suspend fun updateUser(email: String, user: RegisterUser): User? {
        dbQuery {
            UserTable.update({ UserTable.email eq email }) {
                it[name] = user.name
                it[designation] = user.designation
                it[lastUpdatedAt] = LocalDateTime.now()
            }
        }
        return fetchUser(email)
    }

    override suspend fun fetchUser(email: String): User? {
        return dbQuery {
            UserTable.select { UserTable.email.eq(email) }
                .map { getUserInfo(it) }.singleOrNull()
        }
    }

    private fun getUserInfo(row: ResultRow?) : User? {
        return if (row == null) null
        else User(id = row[UserTable.id],
            email = row[UserTable.email],
            name = row[UserTable.name],
            designation = row[UserTable.designation],
            authToken = row[UserTable.authToken],
            last_updated_at = row[UserTable.lastUpdatedAt].toString())
    }
}