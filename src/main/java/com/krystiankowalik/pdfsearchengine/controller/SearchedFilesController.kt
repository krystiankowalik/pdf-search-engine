package com.krystiankowalik.pdfsearchengine.controller

import com.krystiankowalik.pdfsearchengine.io.FileOpener
import com.krystiankowalik.pdfsearchengine.io.RecursiveFileLister
import com.krystiankowalik.pdfsearchengine.model.SearchedFile
import com.krystiankowalik.pdfsearchengine.model.dao.searchedfiles.SearchedFileDao
import com.krystiankowalik.pdfsearchengine.model.dao.searchedfiles.SearchedFileDmo
import com.krystiankowalik.pdfsearchengine.pdf.extractor.PdfTextExtractorImpl
import com.krystiankowalik.pdfsearchengine.view.searchedfiles.SearchedFilesView
import tornadofx.*
import java.io.File

class SearchedFilesController : Controller() {

    private val fileDialogController: FileDialogController by inject()
    private val fileOpener: FileOpener by inject()
    private val view: SearchedFilesView by inject()

    fun pickSearchFolder() {

        val pickedFolder = fileDialogController.pickFolder(view)
        if (pickedFolder.isNotBlank()) {
            view.baseSearchFolder.text = pickedFolder
            importSearchedFiles()
        }
    }

    fun importSearchedFiles() {
        SearchedFileDmo.createTable()
        importSearchedFilesTask().run()
    }

    private fun importSearchedFilesTask() =
            view.root.runAsyncWithOverlay {
                val files = listFiles(view.baseSearchFolder.text)
                        .map { SearchedFile(0, it, getPdfText(it)) }
                files.forEach({
                    println("Processing file ${files.indexOf(it)+1}/${files.size}: ${File(it.path).name} (saving into db)")

                    saveInDb(it)
                })
                getFromDb()
            } ui {
                view.filesList.setAll(it)
            }

    private fun getPdfText(filePath: String) =
            PdfTextExtractorImpl(filePath).getText().replace("'", "")

    private fun saveInDb(searchedFile: SearchedFile) = SearchedFileDao.insert(searchedFile)

    private fun getFromDb() = SearchedFileDao.getAll()

    private fun listFiles(basePath: String): List<String> {
        val fileLister = RecursiveFileLister(basePath)
        return fileLister.list("pdf").map { it.toString() }
    }

    fun openFile(path: String) {
        view.root.runAsyncWithOverlay {
            fileOpener.openFile(path)
        }
    }

}