package com.krystiankowalik.pdfsearchengine.model.dao

interface Dmo {

    val tableName: String

    fun createTable()

    fun dropTable()

}