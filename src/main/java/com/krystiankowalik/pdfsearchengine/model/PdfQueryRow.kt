package com.krystiankowalik.pdfsearchengine.model

data class PdfQueryRow(val stringsToQuery: MutableList<String>,
                       val hits: MutableSet<String>
) {
    constructor() : this(mutableListOf(), mutableSetOf())
}