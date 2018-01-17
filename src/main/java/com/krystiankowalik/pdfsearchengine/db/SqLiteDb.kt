package com.krystiankowalik.pdfsearchengine.db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object SqLiteDb {

    internal lateinit var connection: Connection

    internal fun inTransaction(function: () -> Unit) {
        val url = "jdbc:sqlite::memory"
        try {
            connection = DriverManager.getConnection(url)

            function()

        } catch (e: SQLException) {
            println(e.message)
        } finally {
            connection.close()
        }
    }

    internal fun executeStatement(sql: String) {
        inTransaction {
            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            statement.close()
        }
    }



}