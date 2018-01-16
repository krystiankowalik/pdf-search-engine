package com.krystiankowalik.pdfsearchengine.view.search

import com.krystiankowalik.pdfsearchengine.controller.FileOpenController
import com.krystiankowalik.pdfsearchengine.model.PdfQuery
import com.krystiankowalik.pdfsearchengine.pdf.searcher.BatchPdfSearcherImpl
import com.krystiankowalik.pdfsearchengine.util.whenNotNull
import com.krystiankowalik.pdfsearchengine.view.query.QueryView
import com.krystiankowalik.pdfsearchengine.view.searchedfiles.SearchedFilesView
import javafx.collections.FXCollections
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import tornadofx.*
import java.io.File

class SearchAllFilesFragment(pdfQuery: PdfQuery) : Fragment() {

    private val queryView: QueryView by inject()
    private val searchedFilesView: SearchedFilesView by inject()

    private val matchingPdfFiles = FXCollections.observableArrayList<File>()

    private val searchIcon = resources.imageview("/image/icon_search.png")

    private val batchPdfSearcher = BatchPdfSearcherImpl(searchedFilesView.filesList.map { File(it) })

    private val fileOpenController = FileOpenController()

    private lateinit var textField: TextField
    private lateinit var searchButton: Button

    private fun runSearchTask() {
        searchButton.runAsyncWithProgress {
            batchPdfSearcher.getAllFilesContainingTerm(Regex(textField.text))
        } ui {
            with(matchingPdfFiles) {
                setAll(it)
            }
        }
    }

    override val root = vbox {
        title = "Searching for: ${pdfQuery.description}"
        prefWidth = 500.0

        hbox {

            textField = textfield {
                promptText = "Search in all listed files"
                shortcut("Enter") {
                    runSearchTask()
                }

                hgrow = Priority.ALWAYS
            }

            searchButton = button("", searchIcon) {
                action {
                    runSearchTask()
                }
            }
        }

        val pdfFilesListView = listview(matchingPdfFiles) {
            onDoubleClick {
                runAsyncWithProgress {
                    openFile(selectionModel.selectedItem.toString())
                }
            }
        }

        hbox {
            button("OK") {
                action {
                    val selectedFile = pdfFilesListView.selectionModel.selectedItem
                    selectedFile?.let {
                        println(it)
                        pdfQuery.hit = it.toString()
                        queryView.updatePdfQuery(queryView.queries.indexOf(pdfQuery), pdfQuery)
                    }
                }
                hgrow = Priority.ALWAYS
            }

            hgrow = Priority.ALWAYS

        }
        hgrow = Priority.ALWAYS

    }

    private fun openFile(path: String) {
        fileOpenController.openFile(path)
    }


}