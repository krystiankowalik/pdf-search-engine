package com.krystiankowalik.pdfsearchengine.pdf

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File

class PdfSearcher(val pdfFilePath: String) {

    private val pdfTextStripper = PDFTextStripper()
    val pdfText = extractTextFromPdfFile(File(pdfFilePath))

    fun containsStrings(stringsToQuery: List<String>): Map<String, Boolean> =
            stringsToQuery.map {
                it to pdfText.contains(it.toLowerCase())
            }.toMap()

    fun containsAny(stringsToQuery: List<String>): Boolean =
            stringsToQuery.any {
                pdfText.contains(it.toLowerCase())
            }

    private fun extractTextFromPdfFile(pdfFile: File): String =
            pdfTextStripper.getText(PDDocument.load(pdfFile))


}
