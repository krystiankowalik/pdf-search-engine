package com.krystiankowalik.pdfsearchengine.pdf.searcher

import java.io.File

class BatchPdfSearcherImpl(private val pdfFiles: List<File>) : BatchPdfSearcher {

    override fun getAllFilesContainingTerm(searchedTerm: Regex): List<File> =
            pdfFiles.filter({
                RegexPdfSearcherImpl(it.toString()).containsRegex(searchedTerm)
            })


}