package com.krystiankowalik.pdfsearchengine.model.dao.searchedfiles

import com.krystiankowalik.pdfsearchengine.db.SqLiteDb
import com.krystiankowalik.pdfsearchengine.model.dao.Dmo

object SearchedFileDmo : Dmo {


    override val tableName = "searched_files"

    override fun dropTable() {
        val sql = "DROP TABLE IF EXISTS ${tableName}"
        SqLiteDb.executeStatement(sql)
    }

    override fun createTable() {
        dropTable()
        val sql = "CREATE TABLE ${tableName} (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "path TEXT NOT NULL, " +
                "contents TEXT NOT NULL)"
        SqLiteDb.executeStatement(sql)
    }

}