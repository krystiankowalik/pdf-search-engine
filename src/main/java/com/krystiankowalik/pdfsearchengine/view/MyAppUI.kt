package com.krystiankowalik.pdfsearchengine.view

import javafx.application.Application
import tornadofx.App

object MyAppUI {

    class TornadoApp : App(MasterView::class)

    @JvmStatic
    fun main(args: Array<String>) {
        Application.launch(TornadoApp::class.java, *args)
    }

}

