package com.krystiankowalik.pdfsearchengine.view.license

import com.krystiankowalik.pdfsearchengine.LICENSE
import tornadofx.*

class LicenseFragment : Fragment() {
    override val root = textarea {
        text = LICENSE.licences.joinToString { it }
        prefWidth = 700.0
        prefHeight = 700.0
        isWrapText=true
    }
}