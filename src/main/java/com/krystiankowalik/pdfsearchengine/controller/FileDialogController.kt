package com.krystiankowalik.pdfsearchengine.controller

import javafx.stage.DirectoryChooser
import javafx.stage.FileChooser
import tornadofx.Controller
import tornadofx.View
import java.io.File

class FileDialogController : Controller() {
    private val fileChooser = FileChooser()
    private val directoryChooser = DirectoryChooser()


    fun pickFile(view: View, extensions: List<String> = listOf("*")): String {
        applyExtensionFilter(extensions)
        val pickedFile = fileChooser.showOpenDialog(view.currentWindow)
        return pickedFile?.toString() ?: ""

    }

    private fun applyExtensionFilter(extensions: List<String>) {
        fileChooser.extensionFilters.clear()

        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("extensions", extensions.map {
            if (it != "*") {
                "*." + it
            } else {
                "*"
            }
        }
        ))
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("All", listOf("*")))
    }

    fun pickFolder(view: View): String {
        val pickedFolder = directoryChooser.showDialog(view.currentWindow)
        return pickedFolder?.toString() ?: ""
    }

}