package com.krystiankowalik.pdfsearchengine.controller

import com.krystiankowalik.pdfsearchengine.io.FileOpener
import com.krystiankowalik.pdfsearchengine.model.dao.searchedfiles.SearchedFileDao
import com.krystiankowalik.pdfsearchengine.view.single.SingleQueryFragment
import tornadofx.*
import java.io.File

class SingleQueryController(val view: SingleQueryFragment) : Controller() {

    private val fileOpener = FileOpener()

    fun search() {
        view.pdfFilesListView.runAsyncWithOverlay {
            getAllFilesContainingTermFromDb(Regex(view.textField.text))
        } ui {
            view.matchingPdfFiles.setAll(it)
        }
    }

    private fun getAllFilesContainingTermFromDb(regex: Regex): List<File> {
        val files = SearchedFileDao.getAll()
        return files.filter { it.contents.contains(regex) }.map { File(it.path) }
    }

    fun openFile(path: String) {
        fileOpener.openFile(path)
    }


}