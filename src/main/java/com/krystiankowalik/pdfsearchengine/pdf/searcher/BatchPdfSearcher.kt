package com.krystiankowalik.pdfsearchengine.pdf.searcher

import java.io.File

interface BatchPdfSearcher {

    fun getAllFilesContainingTerm(searchedTerm: Regex): List<File>
}