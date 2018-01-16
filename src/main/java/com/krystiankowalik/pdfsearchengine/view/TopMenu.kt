package com.krystiankowalik.pdfsearchengine.view

import com.krystiankowalik.pdfsearchengine.controller.TopMenuController
import com.krystiankowalik.pdfsearchengine.view.query.QueryView
import com.krystiankowalik.pdfsearchengine.view.searchedfiles.SearchedFilesView
import tornadofx.*

class TopMenu : View() {

    private val queryView: QueryView by inject()
    private val searchedFilesView: SearchedFilesView by inject()
    private val topMenuController: TopMenuController by inject()

    private val masterView: MasterView by inject()

    override val root = menubar {
        menu("File") {

            item("Run search", "Ctrl+R") {
                action {
                    runSearch()
                }
            }
            item("Save Files in...", "Ctrl+S") {
                action {
                    topMenuController.saveFiles()
                }
            }

        }

        menu("Edit")
        menu("Help")
    }

    private fun runSearch() {
        queryView.root.runAsyncWithOverlay {
            topMenuController.runSearch(queryView.queries, searchedFilesView.filesList)
        } ui {
            println("I got $it from run search")
            queryView.queries.replaceAll({ it })

        }
    }

 /*   private fun saveFiles() {
        masterView.root.runAsyncWithOverlay {
            topMenuController.saveFiles()
        }
    }*/



}




