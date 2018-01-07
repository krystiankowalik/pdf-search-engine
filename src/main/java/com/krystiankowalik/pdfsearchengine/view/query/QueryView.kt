package com.krystiankowalik.pdfsearchengine.view.query

import tornadofx.View
import tornadofx.borderpane

class QueryView : View() {

    override val root = borderpane({
        center(CenterView::class)
        bottom(BottomView::class)
    })
}