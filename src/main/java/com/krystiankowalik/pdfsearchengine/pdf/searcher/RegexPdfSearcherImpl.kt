package com.krystiankowalik.pdfsearchengine.pdf.searcher

import com.krystiankowalik.pdfsearchengine.pdf.extractor.PdfTextExtractor
import com.krystiankowalik.pdfsearchengine.pdf.extractor.PdfTextExtractorImpl

class RegexPdfSearcherImpl(val pdfFilePath: String) : RegexPdfSearcher {

    private val pdfTextExtractor: PdfTextExtractor = PdfTextExtractorImpl(pdfFilePath)

    private val pdfText = pdfTextExtractor.getText()

    override fun containsRegex(regex: Regex) =
            pdfText.contains(regex)


}
