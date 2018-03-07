package com.krystiankowalik.pdfsearchengine.controller

import com.krystiankowalik.pdfsearchengine.io.FileOpener
import com.krystiankowalik.pdfsearchengine.util.whenNotEmpty
import com.krystiankowalik.pdfsearchengine.view.searchedfiles.SearchedFilesView
import com.krystiankowalik.pdfsearchengine.view.single.SingleQueryFragment
import tornadofx.*
import java.io.File

class SingleQueryController(val view: SingleQueryFragment) : Controller() {

    private val fileOpener = FileOpener()
    private val searchedFilesView: SearchedFilesView by inject()

    init {
        view.searchedTextField.text = view.pdfQuery.searchedText.pattern
        search()
    }

    fun search() {
        val searchedText = view.searchedTextField.text
        searchedText.whenNotEmpty {
            view.pdfFilesListView.runAsyncWithOverlay {
                getAllFilesContainingTerm(Regex(searchedText,
                        setOf(RegexOption.MULTILINE, RegexOption.DOT_MATCHES_ALL, RegexOption.IGNORE_CASE)))
            } ui {
                view.matchingPdfFiles.setAll(it)
            }
        }
    }

    private fun getAllFilesContainingTerm(regex: Regex): List<File> {
        val files = searchedFilesView.filesList
        return files.filter { it.contents.contains(regex) }.map { File(it.path) }
    }

    fun openFile(path: String) {
        view.root.runAsyncWithOverlay {
            fileOpener.openFile(path)
        }
    }


}