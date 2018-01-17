package com.krystiankowalik.pdfsearchengine.controller

import com.krystiankowalik.pdfsearchengine.io.FileOpener
import com.krystiankowalik.pdfsearchengine.model.PdfQuery
import com.krystiankowalik.pdfsearchengine.model.dao.searchedfiles.SearchedFileDao
import com.krystiankowalik.pdfsearchengine.pdf.searcher.RegexPdfSearcherImpl
import com.krystiankowalik.pdfsearchengine.util.whenNotEmpty
import com.krystiankowalik.pdfsearchengine.view.TopMenu
import com.krystiankowalik.pdfsearchengine.view.query.QueriesView
import com.krystiankowalik.pdfsearchengine.view.searchedfiles.SearchedFilesView
import javafx.collections.ObservableList
import tornadofx.*
import java.io.File

class TopMenuController : Controller() {

    private val fileDialogController: FileDialogController by inject()
    private val queriesView: QueriesView by inject()
    private val fileOpener: FileOpener by inject()

    private val view: TopMenu by inject()

    fun runSearch() {
        queriesView.root.runAsyncWithOverlay {
            doRunDbSearch(queriesView.queries)
        } ui {
            println("I got $it from run single")
            queriesView.queries.replaceAll({ it })

        }
    }

    private fun doRunSearch(queries: ObservableList<PdfQuery>, filesList: ObservableList<String>): ObservableList<PdfQuery> {
        filesList.forEach({
            val pdfSearcher = RegexPdfSearcherImpl(it.toString())
            queries.forEach({
                if (pdfSearcher.containsRegex(it.searchedText)) {
                    it.hit = pdfSearcher.pdfFilePath
                }
            })
        })

        queries.forEach(::println)
        return queries
    }

    private fun doRunDbSearch(queries: ObservableList<PdfQuery>): ObservableList<PdfQuery> {
        val searchedFiles = SearchedFileDao.getAll()
        searchedFiles.forEach({ file ->
            queries.forEach({ query ->
                if (file.contents.contains(query.searchedText)) {
                    query.hit = file.path
                }
            })

        })
        return queries
    }

    fun saveFiles() {
        val folder = pickFolder()

        folder.whenNotEmpty {
            doSaveFiles(queriesView.queries.filter({ it.hit.isNotEmpty() }), folder)
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

    private fun openFolder(folder: String) = fileOpener.openFile(folder)

}