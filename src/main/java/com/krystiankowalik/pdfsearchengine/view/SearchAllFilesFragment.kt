package com.krystiankowalik.pdfsearchengine.view

import com.krystiankowalik.pdfsearchengine.controller.FileOpenController
import com.krystiankowalik.pdfsearchengine.event.ChangePdfQueryEvent
import com.krystiankowalik.pdfsearchengine.model.PdfQueryNew
import com.krystiankowalik.pdfsearchengine.pdf.searcher.BatchPdfSearcherImpl
import com.krystiankowalik.pdfsearchengine.view.query.CenterView
import com.krystiankowalik.pdfsearchengine.view.query.whenNotNull
import com.krystiankowalik.pdfsearchengine.view.searchedfiles.SearchedFilesView
import javafx.collections.FXCollections
import javafx.scene.Parent
import tornadofx.*
import java.io.File

class SearchAllFilesFragment(pdfQuery: PdfQueryNew) : Fragment() {

    private val centerView: CenterView by inject()
    private val searchedFilesView: SearchedFilesView by inject()

    private val matchingPdfFiles = FXCollections.observableArrayList<File>()

    private val searchIcon = resources.imageview("/image/icon_search.png")

    private val batchPdfSearcher = BatchPdfSearcherImpl(searchedFilesView.filesList.map { File(it) })

    private val fileOpenController = FileOpenController()


    override val root = vbox {
        hbox {
            val textField = textfield {
                promptText = "Search in all listed files"
            }
            button("", searchIcon) {
                action {
                    runAsyncWithProgress {
                        batchPdfSearcher.getAllFilesContainingTerm(Regex(textField.text))
                    } ui {
                        with(matchingPdfFiles) {
                            clear()
                            addAll(it)
                        }
                    }
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
                    selectedFile.whenNotNull {
                        println(selectedFile)
                        pdfQuery.hit = selectedFile.toString()
                        centerView.updatePdfQuery(centerView.queries.indexOf(pdfQuery), pdfQuery)
                    }
                }

            }
            button("Cancel") {
                action {
                    close()
                }
            }
        }

    }

    private fun openFile(path: String) {
        fileOpenController.openFile(path)
    }


}