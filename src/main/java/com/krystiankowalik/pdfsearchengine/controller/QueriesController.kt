package com.krystiankowalik.pdfsearchengine.controller

import com.krystiankowalik.pdfsearchengine.io.FileOpener
import com.krystiankowalik.pdfsearchengine.model.PdfQuery
import com.krystiankowalik.pdfsearchengine.util.whenNotEmpty
import com.krystiankowalik.pdfsearchengine.view.query.QueriesView
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.WorkbookFactory
import tornadofx.*
import java.io.FileInputStream

class QueriesController : Controller() {

    private val formatter = DataFormatter()

    private val view: QueriesView by inject()

    private val fileDialogController: FileDialogController by inject()
    private val fileOpener: FileOpener by inject()


    private fun getQuery(): MutableList<PdfQuery> {
        val workbook = WorkbookFactory.create(FileInputStream(view.queryFilePath.text))
        view.queries.clear()
        val queries = mutableListOf<PdfQuery>()
        val querySheet = workbook.getSheetAt(0)
        (1..querySheet.lastRowNum)
                .asSequence()
                .map { querySheet.getRow(it) }
                .forEach {
                    queries.add(PdfQuery(formatter.formatCellValue(it.first()), formatter.formatCellValue(it.getCell(1)).trim(), ""))
                }
        queries.forEach(::println)
        workbook.close()
        return queries
    }

    fun pickFile(view: View) {
        val pickedFile = fileDialogController.pickFile(view, listOf("xlsx"))
        pickedFile.whenNotEmpty {
            this.view.queryFilePath.text = pickedFile
            readQueryFromFile()
        }
    }

    fun openFile(path: String) {
        view.root.runAsyncWithOverlay {
            fileOpener.openFile(path)
        }
    }

    fun readQueryFromFile() {
        view.queryExtractorButton.runAsyncWithProgress {
            getQuery()
        } ui {
            view.queries.setAll(it)
        }
    }


}