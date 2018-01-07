package com.krystiankowalik.pdfsearchengine.controller

import com.krystiankowalik.pdfsearchengine.model.PdfQueryNew
import com.krystiankowalik.pdfsearchengine.pdf.searcher.RegexPdfSearcherImpl
import javafx.collections.ObservableList
import tornadofx.Controller
import java.io.File
import java.nio.file.Files
import java.time.LocalTime

class TopMenuController : Controller() {


    fun runSearch(queries: ObservableList<PdfQueryNew>?, filesList: ObservableList<String>?): ObservableList<PdfQueryNew> {
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

    fun saveFilesWithChangedNames(fileList: List<PdfQueryNew>, newLocation: String) {
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


}