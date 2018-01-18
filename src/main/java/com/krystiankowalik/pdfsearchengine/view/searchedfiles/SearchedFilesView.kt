package com.krystiankowalik.pdfsearchengine.view.searchedfiles

import com.krystiankowalik.pdfsearchengine.controller.SearchedFilesController
import com.krystiankowalik.pdfsearchengine.model.SearchedFile
import javafx.collections.FXCollections
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import tornadofx.*

class SearchedFilesView : View() {

    private val controller: SearchedFilesController by inject()

    private val openIcon = resources.imageview("/image/folder_open.png")
    private val searchIcon = resources.imageview("/image/icon_search.png")

    lateinit var baseSearchFolder: TextField
    lateinit var searchButton: Button
    lateinit var filePathsListView: ListView<SearchedFile>

    val filesList = FXCollections.observableArrayList<SearchedFile>()

    override val root = vbox {
        hbox {
            baseSearchFolder = textfield() {
                promptText = "Enter single folder's path"
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

        filePathsListView = listview(filesList) {
            onDoubleClick {
                if (!selectionModel.isEmpty) {
                    controller.openFile(selectionModel.selectedItem.path)
                }
            }
            shortcut("Enter") {
                if (!selectionModel.isEmpty) {
                    controller.openFile(selectionModel.selectedItem.path)
                }
            }
            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS
        }

    }


}