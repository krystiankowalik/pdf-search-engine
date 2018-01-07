package com.krystiankowalik.pdfsearchengine.view.query

import com.krystiankowalik.pdfsearchengine.controller.CenterViewController
import com.krystiankowalik.pdfsearchengine.controller.FileDialogController
import com.krystiankowalik.pdfsearchengine.controller.FileOpenController
import com.krystiankowalik.pdfsearchengine.model.PdfQueryNew
import javafx.collections.FXCollections
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import tornadofx.*

class CenterView : View() {

    val queries =
            FXCollections.observableArrayList<PdfQueryNew>()

    lateinit var queryFilePath: TextField

    private val fileDialogController: FileDialogController by inject()
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
                    val pickedFile = fileDialogController.pickFile(this@CenterView, listOf("xlsx"))
                    if (pickedFile.isNotBlank()) {
                        queryFilePath.text = pickedFile
                        if (queryFilePath.text != null)
                            runAsyncWithProgress {
                                centerViewController.getQuery()
                            } ui {
                                queries.clear()
                                queries.addAll(it)
                            }
                    }

                }
            }
            button("", downloadIcon) {
                action {
                    runAsyncWithProgress {
                        centerViewController.getQuery()
                    } ui {
                        queries.clear()
                        queries.addAll(it)
                    }
                }
            }
        }

        tableview(queries) {
            column("Description", PdfQueryNew::description) {
                minWidth = 50.0
                isEditable = true
            }
            column("Searched Text", PdfQueryNew::searchedText) {
                minWidth = 50.0

                isEditable = true
            }

            column("Hit", PdfQueryNew::hit) {
                minWidth = 100.0
                isEditable = true
            }
            onDoubleClick {
                if (!selectionModel.isEmpty) {
                    runAsyncWithProgress {
                        openFile(selectionModel.selectedItem.hit)
                    }
                }
            }
            shortcut("Enter") {
                if (!selectionModel.isEmpty) {
                    runAsyncWithProgress {
                        openFile(selectionModel.selectedItem.hit)
                    }
                }
            }

        }
    }

    private fun openFile(path: String) {
        fileOpenController.openFile(path)
    }


}