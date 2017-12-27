package com.krystiankowalik.pdfsearchengine.excel

class SheetsManagerImpl(override val workbook: Workbook) : SheetsManager(workbook) {
    private val apacheWorkbook = (workbook as WorkbookImpl)
            .apacheWorkbook

    override fun getSheet(index: Int): Sheet {
        val sheet = apacheWorkbook
                .getSheetAt(index)
        return SheetImpl(sheet)
    }

    override fun getSheet(name: String): Sheet {
        val sheet = apacheWorkbook.getSheet(name)
        return SheetImpl(sheet)
    }

}