package com.krystiankowalik.pdfsearchengine.pdf.extractor

import com.krystiankowalik.pdfsearchengine.pdf.encryption.PdfEncryptionCheckerImpl
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File

class PdfTextExtractorImpl(override val pdfFilePath: String) : PdfExtractor(pdfFilePath), PdfTextExtractor {

    private val pdfTextStripper = PDFTextStripper()

    override fun getText(): String {
        return extractTextFromPdfFile(File(pdfFilePath))
    }

    private fun extractTextFromPdfFile(pdfFile: File): String {
        if(PdfEncryptionCheckerImpl(pdfFile).isEncrypted()){
            return ""
        }
        PDDocument.load(pdfFile).use {
            return pdfTextStripper.getText(it)
        }
    }
}