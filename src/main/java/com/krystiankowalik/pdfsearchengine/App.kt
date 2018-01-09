package com.krystiankowalik.pdfsearchengine

import com.krystiankowalik.pdfsearchengine.view.MasterView
import javafx.application.Application
import tornadofx.App

object App {

    class TornadoApp : App(MasterView::class)

    @JvmStatic
    fun main(args: Array<String>) {
      Application.launch(TornadoApp::class.java, *args)
    }

}

