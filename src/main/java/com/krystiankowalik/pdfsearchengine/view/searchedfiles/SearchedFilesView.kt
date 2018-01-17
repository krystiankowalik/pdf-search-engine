package com.krystiankowalik.pdfsearchengine.view.searchedfiles

import com.krystiankowalik.pdfsearchengine.controller.SearchFilesController
import javafx.collections.FXCollections
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import tornadofx.*

class SearchedFilesView : View() {

    private val controller: SearchFilesController by inject()

    private val openIcon = resources.imageview("/image/folder_open.png")
    private val searchIcon = resources.imageview("/image/icon_search.png")

    lateinit var baseSearchFolder: TextField
    lateinit var searchButton: Button

    val filesList = FXCollections.observableArrayList<String>()

    override val root = vbox {
        hbox {
            baseSearchFolder = textfield() {
                promptText = "Enter single folder's path"
                //todo remove the test location!
                text = "/home/wd43/Downloads/testfsfds"
                hgrow = Priority.ALWAYS

            }
            button("", openIcon) {
                action {
                    controller.pickSearchFolder()
                }
            }
            searchButton = button("", searchIcon) {
                action {
                    controller.importSearchedFiles()
                }
            }
        }

        listview(filesList) {
            onDoubleClick {
                if (!selectionModel.isEmpty) {
                    controller.openFile(selectionModel.selectedItem)
                }
            }
            shortcut("Enter") {
                if (!selectionModel.isEmpty) {
                    controller.openFile(selectionModel.selectedItem)
                }
            }
            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS
        }

    }


}