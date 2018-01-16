package com.krystiankowalik.pdfsearchengine.view.query

import com.krystiankowalik.pdfsearchengine.controller.CenterViewController
import com.krystiankowalik.pdfsearchengine.controller.FileOpenController
import com.krystiankowalik.pdfsearchengine.model.PdfQuery
import com.krystiankowalik.pdfsearchengine.view.search.SearchAllFilesFragment
import javafx.collections.FXCollections
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import tornadofx.*

class QueryView : View() {

    val queries =
            FXCollections.observableArrayList<PdfQuery>()

    lateinit var queryFilePath: TextField
    lateinit var queryTableView: TableView<PdfQuery>

    private val centerViewController: CenterViewController by inject()
    private val fileOpenController: FileOpenController by inject()

    private val openIcon = resources.imageview("/image/folder_open.png")
    private val downloadIcon = resources.imageview("/image/icon_download.png")


    override val root = vbox {
        hbox {
            queryFilePath = textfield {
                promptText = "Enter query file path"
                //todo remove the test location!
                text = "/home/wd43/IdeaProjects/pdf-search-engine/src/main/resources/contentnew.xlsx"
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
                            SearchAllFilesFragment(it).openModal()
                        }
                    }

                }
                shortcut("Ctrl+F") {
                    selectedItem?.let {
                        SearchAllFilesFragment(it).openModal()
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
                openFile(queryTableView.selectionModel.selectedItem.hit)
            }
        }
    }

    private fun openFile(path: String) {
        fileOpenController.openFile(path)
    }

    fun updatePdfQuery(index: Int, newPdfQuery: PdfQuery) {
        queries[index] = newPdfQuery
    }


}
