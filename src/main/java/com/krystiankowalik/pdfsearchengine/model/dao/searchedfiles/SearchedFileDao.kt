package com.krystiankowalik.pdfsearchengine.model.dao.searchedfiles

import com.krystiankowalik.pdfsearchengine.db.SqLiteDb
import com.krystiankowalik.pdfsearchengine.model.SearchedFile
import com.krystiankowalik.pdfsearchengine.model.dao.Dao

object SearchedFileDao : Dao<SearchedFile> {

    val tableName = SearchedFileDmo.tableName

    override fun getAll(): List<SearchedFile> {
        val searchedFiles = mutableListOf<SearchedFile>()
        SqLiteDb.inTransaction {
            val statement = SqLiteDb.connection.createStatement()
            val resultsFromDb = statement.executeQuery("SELECT * FROM ${tableName}")

            resultsFromDb?.let {
                while (resultsFromDb.next()) {
                    val id = resultsFromDb.getInt("id")
                    val path = resultsFromDb.getString("path")
                    val contents = resultsFromDb.getString("contents")
                    searchedFiles.add(SearchedFile(id, path, contents))
                }
                resultsFromDb.close()
            }
        }

        return searchedFiles

    }

    override fun get(index: Int): SearchedFile {
        var searchedFile: SearchedFile? = null
        SqLiteDb.inTransaction {
            val statement = SqLiteDb.connection.createStatement()
            val resultsFromDb = statement.executeQuery("SELECT * FROM '${tableName}' WHERE ${tableName}.id = $index LIMIT 1")

            resultsFromDb?.let {
                while (resultsFromDb.next()) {
                    val id = resultsFromDb.getInt("id")
                    val path = resultsFromDb.getString("path")
                    val contents = resultsFromDb.getString("contents")
                    searchedFile = SearchedFile(id, path, contents)
                }
                resultsFromDb.close()
            }
        }

        return searchedFile!!

    }

    override fun update(index: Int, element: SearchedFile) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(index: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insert(element: SearchedFile) {
        SqLiteDb.executeStatement("INSERT INTO ${tableName} (id, path, contents) " +
                "VALUES (1, '${element.path}','${element.contents}')")
    }


}