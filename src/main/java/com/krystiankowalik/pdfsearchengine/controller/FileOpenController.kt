package com.krystiankowalik.pdfsearchengine.controller

import tornadofx.Controller
import java.awt.Desktop
import java.awt.SystemColor.desktop
import java.io.File

class FileOpenController : Controller() {

    private val desktop = Desktop.getDesktop()

    fun openFile(path: String) {
        runAsync {
            if (desktop.isSupported(Desktop.Action.OPEN) && path.isNotBlank()) {
                desktop.open(File(path))
            }
        }

    }


}