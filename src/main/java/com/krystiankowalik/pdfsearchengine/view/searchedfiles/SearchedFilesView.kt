package com.krystiankowalik.pdfsearchengine.view.searchedfiles

import com.krystiankowalik.pdfsearchengine.controller.FileDialogController
import com.krystiankowalik.pdfsearchengine.controller.FileOpenController
import com.krystiankowalik.pdfsearchengine.controller.SearchFilesController
import javafx.collections.FXCollections
import javafx.scene.layout.Priority
import tornadofx.*

class SearchedFilesView : View() {

    private val fileDialogController: FileDialogController by inject()
    private val searchFilesController: SearchFilesController by inject()
    private val fileOpenController: FileOpenController by inject()

    private val openIcon = resources.imageview("/image/folder_open.png")
    private val searchIcon = resources.imageview("/image/icon_search.png")

    val filesList = FXCollections.observableArrayList<String>()

    override val root = vbox {
        hbox {
            val textfield = textfield() {
                promptText = "Enter search folder's path"
                //todo remove the test location!
                text = "/home/wd43/Downloads/testfsfds"
                hgrow = Priority.ALWAYS

            }
            button("", openIcon) {
                action {
                    val pickedFolder = fileDialogController.pickFolder(this@SearchedFilesView)
                    if (pickedFolder.isNotBlank()) {
                        textfield.text = pickedFolder
                        runAsyncWithProgress {
                            searchFilesController.listFiles(textfield.text)
                        } ui {
                            filesList.setAll(it)
                        }
                    }

                }
            }
            button("", searchIcon) {
                action {
                    runAsyncWithProgress {
                        searchFilesController.listFiles(textfield.text)
                    } ui {
                        filesList.clear()
                        filesList.setAll(it)
                    }
                }
            }
        }

        listview(filesList) {
            onDoubleClick {
                if (!selectionModel.isEmpty) {
                        openFile(selectionModel.selectedItem)
                }
            }
            shortcut("Enter") {
                if (!selectionModel.isEmpty) {

                        openFile(selectionModel.selectedItem)
                }
            }
            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS
        }

    }


    fun openFile(path: String) {
        fileOpenController.openFile(path)
    }


}