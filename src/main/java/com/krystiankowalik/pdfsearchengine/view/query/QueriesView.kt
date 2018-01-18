package com.krystiankowalik.pdfsearchengine.view.query

import com.krystiankowalik.pdfsearchengine.controller.QueriesController
import com.krystiankowalik.pdfsearchengine.model.PdfQuery
import com.krystiankowalik.pdfsearchengine.view.single.SingleQueryFragment
import javafx.collections.FXCollections
import javafx.scene.control.Button
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import tornadofx.*


class QueriesView : View() {

    private val controller: QueriesController by inject()

    lateinit var queryFilePath: TextField
    lateinit var queryTableView: TableView<PdfQuery>
    lateinit var queryExtractorButton: Button

    private val openIcon = resources.imageview("/image/folder_open.png")
    private val downloadIcon = resources.imageview("/image/icon_download.png")

    val queries = FXCollections.observableArrayList<PdfQuery>()

    override val root = vbox {
        hbox {
            queryFilePath = textfield {
                promptText = "Enter query file path"
                hgrow = Priority.ALWAYS

            }
            button("", openIcon) {
                action {
                    controller.pickFile(this@QueriesView)
                }

            }
            queryExtractorButton = button("", downloadIcon) {
                action {
                    controller.readQueryFromFile()
                }
            }
        }



        queryTableView = tableview(queries) {
            smartResize()

            column("Description", PdfQuery::description) {}
            column("Searched Text", PdfQuery::searchedText) {}

            column("Hit", PdfQuery::hit) {}
            onDoubleClick {
                openCurrentlySelectedFile()
            }
            shortcut("Enter") {
                openCurrentlySelectedFile()
            }

            contextmenu {
                item("Search") {
                    action {
                        selectedItem?.let {
                            SingleQueryFragment(it).openModal()
                        }
                    }

                }
                shortcut("Ctrl+F") {
                    selectedItem?.let {
                        SingleQueryFragment(it).openModal()
                    }
                }
            }

            hgrow = Priority.ALWAYS
            vgrow = Priority.ALWAYS
        }
        hgrow = Priority.ALWAYS
        vgrow = Priority.ALWAYS
    }

    private fun openCurrentlySelectedFile() {
        if (!queryTableView.selectionModel.isEmpty) {
            controller.openFile(queryTableView.selectionModel.selectedItem.hit)
        }
    }

    fun updatePdfQuery(index: Int, newPdfQuery: PdfQuery) {
        queries[index] = newPdfQuery
    }




}
