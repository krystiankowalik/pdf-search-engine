package com.krystiankowalik.pdfsearchengine

import com.krystiankowalik.pdfsearchengine.view.MasterView
import javafx.application.Application
import javafx.stage.Stage
import tornadofx.App


object App {

    class TornadoApp : App(MasterView::class) {
        override fun start(stage: Stage) {
            super.start(stage)
            stage.isMaximized=true
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        Application.launch(TornadoApp::class.java, *args)
    }




}

