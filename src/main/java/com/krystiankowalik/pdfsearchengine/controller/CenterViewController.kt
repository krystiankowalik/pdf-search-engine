package com.krystiankowalik.pdfsearchengine.controller

import com.krystiankowalik.pdfsearchengine.excel.mutableCell
import com.krystiankowalik.pdfsearchengine.io.RecursiveFileLister
import com.krystiankowalik.pdfsearchengine.model.PdfQueryNew
import com.krystiankowalik.pdfsearchengine.model.PdfQueryRow
import com.krystiankowalik.pdfsearchengine.pdf.searcher.RegexPdfSearcherImpl
import com.krystiankowalik.pdfsearchengine.view.query.CenterView
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.WorkbookFactory
import tornadofx.Controller
import java.io.FileInputStream
import java.io.FileOutputStream

class CenterViewController : Controller() {

    private val formatter = DataFormatter()

    private val centerView: CenterView by inject()
    private val searchFilesController: SearchFilesController by inject()

    fun getQuery(): MutableList<PdfQueryNew> {
        val workbook = WorkbookFactory.create(FileInputStream(centerView.queryFilePath.text))
        centerView.queries.clear()
        val queries = mutableListOf<PdfQueryNew>()
        val querySheet = workbook.getSheetAt(0)
        (1..querySheet.lastRowNum)
                .asSequence()
                .map { querySheet.getRow(it) }
                .forEach {
                    queries.add(PdfQueryNew(formatter.formatCellValue(it.first()), Regex(formatter.formatCellValue(it.getCell(1)).trim()), ""))
                }
        queries.forEach(::println)
        workbook.close()
        return queries
    }


}