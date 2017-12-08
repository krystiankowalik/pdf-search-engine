package com.krystiankowalik.pdfsearchengine

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File

class SimplePDFSearch(val pdfFilePath: String) {

    fun containsStrings(stringsToQuery: List<String>): Map<String, Boolean> {
        val pdfFile = File(pdfFilePath)
        val documentContents = extractTextFromPdfFile(pdfFile).toLowerCase()
        return stringsToQuery.map {
            it to documentContents.contains(it.toLowerCase())
        }.toMap()
    }

    fun extractTextFromPdfFile(pdfFile: File): String =
            PDFTextStripper().getText(PDDocument.load(pdfFile))
}