package com.krystiankowalik.pdfsearchengine.view

import com.krystiankowalik.pdfsearchengine.controller.FileDialogController
import com.krystiankowalik.pdfsearchengine.controller.TopMenuController
import com.krystiankowalik.pdfsearchengine.view.query.QueryView
import com.krystiankowalik.pdfsearchengine.view.searchedfiles.SearchedFilesView
import tornadofx.*

class TopMenu : View() {

    private val fileDialogController: FileDialogController by inject()
    private val queryView: QueryView by inject()
    private val searchedFilesView: SearchedFilesView by inject()
    private val topMenuController: TopMenuController by inject()

    private val masterView: MasterView by inject()

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
                    runSearch()
                }
            }
            item("Save Files in...", "Ctrl+S") {
                action {
                    saveFiles()
                }
            }

        }



        menu("Edit")
        menu("Help")
    }

    private fun runSearch() {
        masterView.root.runAsyncWithOverlay {
            topMenuController.runSearch(queryView.queries, searchedFilesView.filesList)
        } ui {
            queryView.queries.setAll(it)
        }
    }

    private fun saveFiles() {
        val newLocation = fileDialogController.pickFolder(this@TopMenu)
        masterView.root.runAsyncWithOverlay {
            topMenuController.saveFilesWithChangedNames(queryView.queries.filter({ it.hit.isNotEmpty() }), newLocation.toString())
        }
    }

}




