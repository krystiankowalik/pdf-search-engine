package com.krystiankowalik.pdfsearchengine.controller

import com.krystiankowalik.pdfsearchengine.io.FileOpener
import com.krystiankowalik.pdfsearchengine.model.dao.searchedfiles.SearchedFileDao
import com.krystiankowalik.pdfsearchengine.util.whenNotEmpty
import com.krystiankowalik.pdfsearchengine.view.single.SingleQueryFragment
import tornadofx.*
import java.io.File

class SingleQueryController(val view: SingleQueryFragment) : Controller() {

    private val fileOpener = FileOpener()

    init {
        view.searchedTextField.text = view.pdfQuery.searchedText.pattern
        search()
    }

    fun search() {
        val searchedText = view.searchedTextField.text
        searchedText.whenNotEmpty {
            view.pdfFilesListView.runAsyncWithOverlay {
                getAllFilesContainingTermFromDb(Regex(searchedText))
            } ui {
                view.matchingPdfFiles.setAll(it)
            }
        }
    }

    private fun getAllFilesContainingTermFromDb(regex: Regex): List<File> {
        val files = SearchedFileDao.getAll()
        return files.filter { it.contents.contains(regex) }.map { File(it.path) }
    }

    fun openFile(path: String) {
        view.root.runAsyncWithOverlay {
            fileOpener.openFile(path)
        }
    }


}