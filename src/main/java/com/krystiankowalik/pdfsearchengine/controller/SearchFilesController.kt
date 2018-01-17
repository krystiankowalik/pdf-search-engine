package com.krystiankowalik.pdfsearchengine.controller

import com.krystiankowalik.pdfsearchengine.io.FileLister
import com.krystiankowalik.pdfsearchengine.io.FileOpener
import com.krystiankowalik.pdfsearchengine.io.RecursiveFileLister
import com.krystiankowalik.pdfsearchengine.model.SearchedFile
import com.krystiankowalik.pdfsearchengine.model.dao.searchedfiles.SearchedFileDao
import com.krystiankowalik.pdfsearchengine.model.dao.searchedfiles.SearchedFileDmo
import com.krystiankowalik.pdfsearchengine.pdf.extractor.PdfTextExtractorImpl
import com.krystiankowalik.pdfsearchengine.view.searchedfiles.SearchedFilesView
import tornadofx.*
import java.util.concurrent.CountDownLatch

class SearchFilesController : Controller() {

    private val fileDialogController: FileDialogController by inject()
    private val fileOpener: FileOpener by inject()
    private val view: SearchedFilesView by inject()

    private lateinit var fileLister: FileLister

    fun pickSearchFolder() {

        println(SearchedFileDao.getAll().size)

        val pickedFolder = fileDialogController.pickFolder(view)
        if (pickedFolder.isNotBlank()) {
            view.baseSearchFolder.text = pickedFolder
            importSearchedFiles()
        }
    }

    private fun saveInDb(filePath: String) {
        val pdfText = PdfTextExtractorImpl(filePath).getText()
        SearchedFileDao.insert(SearchedFile(SearchedFileDao.size(), filePath, pdfText))
    }

    fun importSearchedFiles() {
        SearchedFileDmo.createTable()
        importSearchedFilesTask.run()
    }

    private val importSearchedFilesTask = view.searchButton.runAsyncWithProgress {
        val files = listFiles(view.baseSearchFolder.text)
        files.forEach({ saveInDb(it) })
        files
    } ui {
        view.filesList.setAll(it)
    }

    private fun listFiles(basePath: String): List<String> {
        fileLister = RecursiveFileLister(basePath)
        return fileLister.list("pdf").map { it.toString() }
    }

    fun openFile(path: String) {
        fileOpener.openFile(path)
    }
}