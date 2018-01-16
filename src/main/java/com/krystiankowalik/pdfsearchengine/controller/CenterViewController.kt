package com.krystiankowalik.pdfsearchengine.controller

import com.krystiankowalik.pdfsearchengine.model.PdfQuery
import com.krystiankowalik.pdfsearchengine.util.whenNotEmpty
import com.krystiankowalik.pdfsearchengine.view.query.QueryView
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.WorkbookFactory
import tornadofx.*
import java.io.FileInputStream

class CenterViewController : Controller() {

    private val formatter = DataFormatter()

    private val queryView: QueryView by inject()

    private val fileDialogController: FileDialogController by inject()


    fun getQuery(): MutableList<PdfQuery> {
        val workbook = WorkbookFactory.create(FileInputStream(queryView.queryFilePath.text))
        queryView.queries.clear()
        val queries = mutableListOf<PdfQuery>()
        val querySheet = workbook.getSheetAt(0)
        (1..querySheet.lastRowNum)
                .asSequence()
                .map { querySheet.getRow(it) }
                .forEach {
                    queries.add(PdfQuery(formatter.formatCellValue(it.first()), Regex(formatter.formatCellValue(it.getCell(1)).trim()), ""))
                }
        queries.forEach(::println)
        workbook.close()
        return queries
    }

    fun pickFile(view: View) {
        val pickedFile = fileDialogController.pickFile(view, listOf("xlsx"))
        pickedFile.whenNotEmpty {
            queryView.queryFilePath.text = pickedFile
            queryView.readQueryFromFile()
        }
    }

    fun readQueryFromFile() {
        queryView.readQueryFromFile()
    }


}