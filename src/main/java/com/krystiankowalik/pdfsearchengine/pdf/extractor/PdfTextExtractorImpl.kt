package com.krystiankowalik.pdfsearchengine.pdf.extractor

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File

class PdfTextExtractorImpl(override val pdfFilePath: String) : PdfExtractor(pdfFilePath), PdfTextExtractor {

    private val pdfTextStripper = PDFTextStripper()

    override fun getText(): String {
        return extractTextFromPdfFile(File(pdfFilePath))
    }

    private fun extractTextFromPdfFile(pdfFile: File): String {
        try {
            PDDocument.load(pdfFile).use {
                return pdfTextStripper.getText(it)
            }
        } catch (e: InvalidPasswordException) {
            return ""
        }
    }
}