import com.krystiankowalik.pdfsearchengine.Control

/*package com.krystiankowalik.pdfsearchengine

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage


class App : tornadofx.App() {
    override fun start(stage: Stage) {
        val mainContainer = VBox()

        val textField = TextField()
        textField.promptText = "Enter base pdf search folder"
        textField.isFocused
        val button = Button()
        button.isDefaultButton = true

        button.requestFocus()
        mainContainer.children.addAll(textField, button)
        val scene = Scene(mainContainer, 200.0, 200.0)

        stage.scene = scene
        stage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(App::class.java)
        }
    }
}*/

class App() {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Control().run(args[0], args[1], args[2])

        }
    }
}
