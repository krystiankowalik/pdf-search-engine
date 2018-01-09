package com.krystiankowalik.pdfsearchengine.event

import com.krystiankowalik.pdfsearchengine.model.PdfQueryNew
import tornadofx.*

class ChangePdfQueryEvent(val index: Int, val pdfQuery: PdfQueryNew) : FXEvent(EventBus.RunOn.ApplicationThread)