package com.krystiankowalik.pdfsearchengine.controller


import com.krystiankowalik.pdfsearchengine.io.FileOpener
import com.krystiankowalik.pdfsearchengine.io.RecursiveFileLister
import com.krystiankowalik.pdfsearchengine.model.SearchedFile
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
        importSearchedFilesTask().run()
    }

    private fun importSearchedFilesTask() =
            view.root.runAsyncWithOverlay {
                val rawFiles = listFiles(view.baseSearchFolder.text)
                val files = rawFiles.map {
                    println("Processing file ${rawFiles.indexOf(it)+1}/${rawFiles.size}: ${File(it).name} (extracting text from PDF)")
                    SearchedFile(0, it, getPdfText(it))
                }

                files
            } ui {
                view.filesList.setAll(it)
            }

    private fun getPdfText(filePath: String) =
            PdfTextExtractorImpl(filePath).getText().replace("'", "")

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