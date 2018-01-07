package com.krystiankowalik.pdfsearchengine.view

import com.krystiankowalik.pdfsearchengine.controller.FileDialogController
import com.krystiankowalik.pdfsearchengine.controller.TopMenuController
import com.krystiankowalik.pdfsearchengine.view.query.CenterView
import com.krystiankowalik.pdfsearchengine.view.searchedfiles.SearchedFilesView
import tornadofx.*

class TopMenu : View() {

    private val fileDialogController: FileDialogController by inject()
    private val centerView: CenterView by inject()
    private val searchedFilesView: SearchedFilesView by inject()
    private val topMenuController: TopMenuController by inject()

    override val root = menubar {
        menu("File") {
            /*
                        item("Open query", "Ctrl+O") {
                            action {
                                println(fileDialogController.pickFile(this@TopMenu, listOf("xlsx", "xlsb")))
                            }
                        }
                        item("Save report", "Ctrl+S") {
                            action {
                                println(fileDialogController.saveFile(this@TopMenu))
                            }
                        }
            */
            item("Run search", "Ctrl+R") {
                action {
                    runAsyncWithOverlay {
                        topMenuController.runSearch(centerView.queries, searchedFilesView.filesList)
                    } ui {
                        centerView.queries.replaceAll { it }
                    }
                }
            }
            item("Save Files in...", "Ctrl+S") {
                action {
                    val newLocation = fileDialogController.pickFolder(this@TopMenu)
                    if (newLocation != null) {
                        runAsyncWithOverlay {
                            topMenuController.saveFilesWithChangedNames(centerView.queries.filter({ it.hit.isNotEmpty() }), newLocation.toString())
                        }
                    }

                }
            }

        }



        menu("Edit")
        menu("Help")
    }

}


