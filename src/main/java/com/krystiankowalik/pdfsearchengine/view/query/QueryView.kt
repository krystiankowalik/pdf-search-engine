package com.krystiankowalik.pdfsearchengine.view.query

import com.krystiankowalik.pdfsearchengine.controller.CenterViewController
import com.krystiankowalik.pdfsearchengine.controller.FileOpenController
import com.krystiankowalik.pdfsearchengine.model.PdfQueryNew
import com.krystiankowalik.pdfsearchengine.util.whenNotNull
import com.krystiankowalik.pdfsearchengine.view.search.SearchAllFilesFragment
import javafx.collections.FXCollections
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import tornadofx.*

class QueryView : View() {

    val queries =
            FXCollections.observableArrayList<PdfQueryNew>()

    lateinit var queryFilePath: TextField
    lateinit var queryTableView: TableView<PdfQueryNew>

    private val centerViewController: CenterViewController by inject()
    private val fileOpenController: FileOpenController by inject()

    private val openIcon = resources.imageview("/image/folder_open.png")
    private val downloadIcon = resources.imageview("/image/icon_download.png")


    override val root = vbox {
        hbox {
            queryFilePath = textfield {
                promptText = "Enter query file path"
                hgrow = Priority.ALWAYS

            }
            button("", openIcon) {
                action {
                    centerViewController.pickFile(this@QueryView)
                }

            }
            button("", downloadIcon) {
                action {
                    centerViewController.readQueryFromFile()
                }
            }
        }



        queryTableView = tableview(queries)
        {
            column("Description", PdfQueryNew::description) {}
            column("Searched Text", PdfQueryNew::searchedText) {}

            column("Hit", PdfQueryNew::hit) {}
            onDoubleClick {
                openCurrentlySelectedFile()
            }
            shortcut("Enter") {
                openCurrentlySelectedFile()
            }

            contextmenu {
                item("Search") {
                    action {
                        selectedItem.whenNotNull {
                            SearchAllFilesFragment(selectedItem!!).openModal()
                        }
                    }

                }
            }

            hgrow = Priority.ALWAYS
            vgrow = Priority.ALWAYS
        }
        hgrow = Priority.ALWAYS
        vgrow = Priority.ALWAYS
    }


    fun readQueryFromFile() {
        root.runAsyncWithOverlay {
            centerViewController.getQuery()
        } ui {
            queries.setAll(it)
        }
    }

    private fun openCurrentlySelectedFile() {
        if (!queryTableView.selectionModel.isEmpty) {
            root.runAsyncWithOverlay {
                openFile(queryTableView.selectionModel.selectedItem.toString())
            }
        }
    }

    private fun openFile(path: String) {
        fileOpenController.openFile(path)
    }

    fun updatePdfQuery(index: Int, newPdfQuery: PdfQueryNew) {
        queries[index] = newPdfQuery

    }


}
