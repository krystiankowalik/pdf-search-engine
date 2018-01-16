package com.krystiankowalik.pdfsearchengine.controller

import com.krystiankowalik.pdfsearchengine.io.FileOpener
import com.krystiankowalik.pdfsearchengine.pdf.searcher.BatchPdfSearcherImpl
import com.krystiankowalik.pdfsearchengine.view.searchedfiles.SearchedFilesView
import com.krystiankowalik.pdfsearchengine.view.single.SingleQueryFragment
import tornadofx.*
import java.io.File

class SingleQueryController(val view: SingleQueryFragment) : Controller() {

    private val searchedFilesView: SearchedFilesView by inject()
    private val batchPdfSearcher = BatchPdfSearcherImpl(searchedFilesView.filesList.map { File(it) })
    private val fileOpener = FileOpener()


    fun search() {
        view.pdfFilesListView.runAsyncWithOverlay {
            batchPdfSearcher.getAllFilesContainingTerm(Regex(view.textField.text))
        } ui {
            view.matchingPdfFiles.setAll(it)
        }
    }

    fun openFile(path: String) {
        fileOpener.openFile(path)
    }


}