package com.krystiankowalik.pdfsearchengine.pdf.searcher

interface RegexPdfSearcher {

    fun containsRegex(regex: Regex): Boolean
}