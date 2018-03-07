package com.krystiankowalik.pdfsearchengine.view

import com.krystiankowalik.pdfsearchengine.controller.TopMenuController
import com.krystiankowalik.pdfsearchengine.view.license.LicenseFragment
import tornadofx.*

class TopMenu : View() {

    private val controller: TopMenuController by inject()

    override val root = menubar {
        menu("File") {

            item("Run search", "Ctrl+R") {
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

        menu("Edit"){
            item("Copy queries","Ctrl+C"){
                action{
                    controller.copyQueriesToClipboard()
                }
            }
        }
        menu("Help"){
            item("About") {
                action {
                    LicenseFragment().openModal()
                }
            }
        }
    }


}




