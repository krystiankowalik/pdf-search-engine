package com.krystiankowalik.pdfsearchengine.view.single

import com.krystiankowalik.pdfsearchengine.controller.SingleQueryController
import com.krystiankowalik.pdfsearchengine.io.FileOpener
import com.krystiankowalik.pdfsearchengine.model.PdfQuery
import com.krystiankowalik.pdfsearchengine.view.query.QueriesView
import javafx.collections.FXCollections
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import tornadofx.*
import java.io.File

class SingleQueryFragment(pdfQuery: PdfQuery) : Fragment() {

    private val controller = SingleQueryController(this)

    private val queriesView: QueriesView by inject()

    private val searchIcon = resources.imageview("/image/icon_search.png")

    lateinit var textField: TextField
    lateinit var searchButton: Button
    lateinit var pdfFilesListView: ListView<File>

    val matchingPdfFiles = FXCollections.observableArrayList<File>()

    override val root = vbox {
        title = "Searching for: ${pdfQuery.description}"
        prefWidth = 500.0

        hbox {

            textField = textfield {
                promptText = "Search in all listed files"
                shortcut("Enter") {
                    controller.search()
                }

                hgrow = Priority.ALWAYS
            }

            searchButton = button("", searchIcon) {
                action {
                    controller.search()
                }
            }
        }

        pdfFilesListView = listview(matchingPdfFiles) {
            onDoubleClick {
                controller.openFile(selectionModel.selectedItem.toString())
            }
        }

        hbox {
            button("OK") {
                action {
                    val selectedFile = pdfFilesListView.selectionModel.selectedItem
                    selectedFile?.let {
                        pdfQuery.hit = it.toString()
                        queriesView.updatePdfQuery(queriesView.queries.indexOf(pdfQuery), pdfQuery)
                        close()
                    }
                }
                hgrow = Priority.ALWAYS
                vgrow = Priority.ALWAYS

            }

            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS

        }
        vgrow = Priority.ALWAYS
        hgrow = Priority.ALWAYS

    }


}