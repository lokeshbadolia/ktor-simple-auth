package com.codexcollab.service

import com.codexcollab.database.DatabaseFactory.dbQuery
import com.codexcollab.database.tables.AuthTable
import com.codexcollab.encrypt.hash
import com.codexcollab.model.Auth
import com.codexcollab.model.RegisterAuth
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class AuthServiceUseCase : AuthService {
   /* override suspend fun registerUser(params: createUserParam): User? {
        var statment:InsertStatement<Number>?=null
       dbQuery {

           statment=UserTable.insert {
               it[full_name]=params.full_name
               it[email]=params.email
               it[password]=hash(params.password)
               it[profile]=params.profile
           }

       }
        return rowToUser(statment?.resultedValues?.get(0))
    }

    override suspend fun findUserByEmail(email: String): User? {
        val user= dbQuery {
            UserTable.select {
                UserTable.email.eq(email)
            }.map { rowToUser(it) }.singleOrNull()

            }
        return user
        }

    override suspend fun loginUser(email:String,password:String): User? {
        val user= dbQuery {
            UserTable.select {
                UserTable.email eq email and  (UserTable.password eq hash(password))
            }.map { rowToUser(it) }.singleOrNull()
        }
        return user
    }*/

    override suspend fun login(email: String, password: String): Auth? {
        return dbQuery {
            AuthTable.select {
                AuthTable.email eq email and  (AuthTable.password eq hash(password))
            }.map { getAuthResult(it) }.singleOrNull()
        }
    }

    override suspend fun register(register: RegisterAuth): Auth? {
        var stmt: InsertStatement<Number>? = null
        dbQuery {
            stmt = AuthTable.insert {
                it[email] = register.email
                it[password] = hash(register.password)
            }
        }
        return getAuthResult(stmt?.resultedValues?.get(0))
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
        else Auth(id = row[AuthTable.id], email = row[AuthTable.email], createdAt = row[AuthTable.createdAt].toString())
    }
}