package com.krystiankowalik.pdfsearchengine.controller

import com.krystiankowalik.pdfsearchengine.io.FileOpener
import com.krystiankowalik.pdfsearchengine.model.PdfQuery
import com.krystiankowalik.pdfsearchengine.util.whenNotEmpty
import com.krystiankowalik.pdfsearchengine.view.TopMenu
import com.krystiankowalik.pdfsearchengine.view.query.QueriesView
import com.krystiankowalik.pdfsearchengine.view.searchedfiles.SearchedFilesView
import javafx.collections.ObservableList
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import tornadofx.*
import java.io.File

class TopMenuController : Controller() {

    private val fileDialogController: FileDialogController by inject()
    private val queriesView: QueriesView by inject()
    private val fileOpener: FileOpener by inject()
    private val searchedFilesView: SearchedFilesView by inject()

    private val view: TopMenu by inject()

    fun runSearch() {
        queriesView.root.runAsyncWithOverlay {
            runInMemorySearch(queriesView.queries)
        } ui {
            println("I got $it from search")
            queriesView.queries.replaceAll({ it })

        }
    }

    private fun runInMemorySearch(queries: ObservableList<PdfQuery>): ObservableList<PdfQuery> {
        val searchedFiles = searchedFilesView.filesList
        searchedFiles.forEach({ file ->
            println("Processing file ${searchedFiles.indexOf(file) + 1}/${searchedFiles.size}: ${File(file.path).name} (searching)")
            queries.forEach({ query ->
                if (query.hit.isBlank() && file.contents.contains(query.searchedText)) {
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
        return File(this.parent + File.separator + this.nameWithoutExtension + "_" + System.nanoTime() + "." + this.extension)
    }

    private fun openFolder(folder: String) = fileOpener.openFile(folder)

    fun copyQueriesToClipboard() {
        println("I'm trying to copy ${queriesView.queries}")
        //queriesView.queries.let {
            val sb = StringBuilder()
            queriesView.queries.forEach({
                sb.append(it.description)
                sb.append("\t")
                sb.append(it.searchedText)
                sb.append("\t")
                sb.append(it.hit)
                sb.append(System.lineSeparator())
            })
            val clipboardContent = ClipboardContent()
            clipboardContent.putString(sb.toString())

            // set clipboard content
            Clipboard.getSystemClipboard().setContent(clipboardContent)
        //}
    }

}