package com.krystiankowalik.pdfsearchengine.excel

import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream
import java.io.FileOutputStream

class WorkbookImpl(override val path: String, override val password: String = "") : Workbook(path, password) {
    override fun new() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val workbookInputStream = FileInputStream(path)
    internal val apacheWorkbook: org.apache.poi.ss.usermodel.Workbook = WorkbookFactory.create(workbookInputStream, password)

    override fun save(newPath: String, password: String) {
        FileOutputStream(path).use {
            apacheWorkbook.write(it)
        }
    }

    override fun save(password: String ) {
        save(path,password)
    }

    override fun getSheetsManager(): SheetsManager =
            SheetsManagerImpl(this)


    override fun close() {
        workbookInputStream.close()
    }


}