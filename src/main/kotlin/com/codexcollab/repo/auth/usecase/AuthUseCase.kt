package com.codexcollab.repo.auth.usecase

import com.codexcollab.database.connection.DatabaseFactory.dbQuery
import com.codexcollab.database.tables.AuthTable
import com.codexcollab.database.tables.UserTable
import com.codexcollab.security.jwt.JwtConfig
import com.codexcollab.security.hash.hash
import com.codexcollab.model.response.Auth
import com.codexcollab.model.request.RegisterAuth
import com.codexcollab.repo.auth.gateway.AuthUseCaseImpl
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthUseCase : AuthUseCaseImpl, KoinComponent {

    private val jwt by inject<JwtConfig>()
    override suspend fun login(userEmail: String, password: String): Auth? {
        val authResult = dbQuery {
            AuthTable.select {
                AuthTable.email eq userEmail and  (AuthTable.password eq hash(password))
            }.map { getAuthResult(it) }.singleOrNull()
        }
        if (authResult != null){
            val token = jwt.createAuthToken(authResult.id, authResult.email)
            dbQuery {
                UserTable.update({ UserTable.email eq userEmail }) {
                    it[authToken] = token
                }
            }
            authResult.auth_token = token
        }
        return authResult
    }

    override suspend fun register(register: RegisterAuth): Auth? {
        var stmt: InsertStatement<Number>? = null
        dbQuery {
            stmt = AuthTable.insert {
                it[email] = register.email
                it[password] = hash(register.password)
            }
        }
        val authResult = getAuthResult(stmt?.resultedValues?.get(0))
        if (authResult != null){
            val token = jwt.createAuthToken(authResult.id, authResult.email)
            if (checkIfAccountExists(register.email) != null){
                dbQuery {
                    UserTable.insert {
                        it[email] = register.email
                        it[authToken] = token
                    }
                }
            }else{
                dbQuery {
                    UserTable.update {
                        it[email] = register.email
                        it[authToken] = token
                    }
                }
            }
            authResult.auth_token = token
        }
        return authResult
    }

    override suspend fun checkIfAccountExists(email: String): Auth? {
        val authResult = dbQuery {
            AuthTable.select { AuthTable.email.eq(email) }
                .map { getAuthResult(it) }.singleOrNull()
        }
        return authResult
    }

    private fun getAuthResult(row: ResultRow?) : Auth? {
        return if (row == null) null
        else Auth(id = row[AuthTable.id], email = row[AuthTable.email], created_at = row[AuthTable.createdAt].toString())
    }
}