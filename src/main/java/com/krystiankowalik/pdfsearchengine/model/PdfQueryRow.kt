package com.krystiankowalik.pdfsearchengine.model

data class PdfQueryRow(var regex: Regex,
                       val hits: MutableSet<String>
)