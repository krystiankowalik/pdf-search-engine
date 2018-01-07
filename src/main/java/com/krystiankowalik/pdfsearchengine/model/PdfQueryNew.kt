package com.krystiankowalik.pdfsearchengine.model

data class PdfQueryNew(var description: String,
                       var searchedText: Regex,
                       var hit: String
)