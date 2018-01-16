package com.krystiankowalik.pdfsearchengine.controller

import com.krystiankowalik.pdfsearchengine.model.PdfQuery
import com.krystiankowalik.pdfsearchengine.pdf.searcher.RegexPdfSearcherImpl
import com.krystiankowalik.pdfsearchengine.util.whenNotEmpty
import com.krystiankowalik.pdfsearchengine.view.TopMenu
import com.krystiankowalik.pdfsearchengine.view.query.QueryView
import javafx.collections.ObservableList
import tornadofx.*
import java.io.File

class TopMenuController : Controller() {

    private val fileDialogController: FileDialogController by inject()
    private val queryView: QueryView by inject()
    private val fileOpenController: FileOpenController by inject()


    private val view: TopMenu by inject()


    fun runSearch(queries: ObservableList<PdfQuery>?, filesList: ObservableList<String>?): ObservableList<PdfQuery> {
        filesList?.forEach({
            val pdfSearcher = RegexPdfSearcherImpl(it.toString())
            queries?.forEach({
                if (pdfSearcher.containsRegex(it.searchedText)) {
                    it.hit = pdfSearcher.pdfFilePath
                }
            })
        })

        queries?.forEach(::println)
        return queries!!
    }

    fun saveFiles() {
        val folder = pickFolder()

        folder.whenNotEmpty {
            doSaveFiles(queryView.queries.filter({ it.hit.isNotEmpty() }), folder)
        }

        openFolder(folder)
    }

    private fun pickFolder() = fileDialogController.pickFolder(view)

    private fun doSaveFiles(files: List<PdfQuery>, targetFolder: String) {
        view.root.runAsyncWithOverlay {
            saveFilesWithChangedNames(files, targetFolder)
        }
    }

    private fun saveFilesWithChangedNames(fileList: List<PdfQuery>, newLocation: String) {
        fileList
                .forEach({
                    copyFile(File(it.hit), File(newLocation + File.separator + it.description + ".pdf"))
                })

    }

    private fun copyFile(file: File, newFile: File) {
        if (newFile.exists()) {
            file.copyTo(newFile.handleDuplicate())
        } else {
            file.copyTo(newFile)
        }
    }

    private fun File.handleDuplicate(): File {
        return File(this.parent + File.separator + this.nameWithoutExtension + "_" + System.nanoTime() + this.extension)
    }

    private fun openFolder(folder: String) = fileOpenController.openFile(folder)

}