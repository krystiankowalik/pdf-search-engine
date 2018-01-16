package com.krystiankowalik.pdfsearchengine.io

import tornadofx.Controller
import java.awt.Desktop
import java.io.File

class FileOpener : Controller() {

    private val desktop = Desktop.getDesktop()

    fun openFile(path: String) {
        runAsync {
            if (desktop.isSupported(Desktop.Action.OPEN) && path.isNotBlank()) {
                desktop.open(File(path))
            }
        }

    }


}