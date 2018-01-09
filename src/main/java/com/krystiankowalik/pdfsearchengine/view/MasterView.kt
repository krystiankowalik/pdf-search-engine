package com.krystiankowalik.pdfsearchengine.view

import com.krystiankowalik.pdfsearchengine.view.query.QueryView
import com.krystiankowalik.pdfsearchengine.view.searchedfiles.SearchedFilesView
import javafx.scene.control.TabPane
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import tornadofx.*

class MasterView : View() {
    override val root = vbox {

        add(TopMenu::class)

        tabpane {
            tab("Files to search", Pane()) {
                add(SearchedFilesView::class)
            }
            tab("Query", Pane()) {
                add(QueryView::class)
            }

            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE


            hgrow = Priority.ALWAYS
            vgrow = Priority.ALWAYS

        }
        vgrow = Priority.ALWAYS
        hgrow = Priority.ALWAYS
    }

}









