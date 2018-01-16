package com.krystiankowalik.pdfsearchengine.view

import com.krystiankowalik.pdfsearchengine.controller.TopMenuController
import com.krystiankowalik.pdfsearchengine.view.query.QueriesView
import com.krystiankowalik.pdfsearchengine.view.searchedfiles.SearchedFilesView
import tornadofx.*

class TopMenu : View() {

    private val controller: TopMenuController by inject()

    override val root = menubar {
        menu("File") {

            item("Run single", "Ctrl+R") {
                action {
                    controller.runSearch()
                }
            }
            item("Save Files in...", "Ctrl+S") {
                action {
                    controller.saveFiles()
                }
            }

        }

        menu("Edit")
        menu("Help")
    }


}




