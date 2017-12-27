package com.krystiankowalik.pdfsearchengine.excel

abstract class Workbook(open val path: String, open val password: String) : AutoCloseable {


    abstract fun new()

    abstract fun save(password: String="")

    abstract fun save(newPath: String, password: String = "")

    abstract fun getSheetsManager(): SheetsManager

    override fun close() {}

}