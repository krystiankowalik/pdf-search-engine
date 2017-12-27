package com.krystiankowalik.pdfsearchengine.excel

abstract class SheetsManager(open val workbook: Workbook) {

    abstract fun getSheet(index:Int) : Sheet

    abstract fun getSheet(name: String): Sheet
}