package com.krystiankowalik.pdfsearchengine.pdf.searcher

import com.krystiankowalik.pdfsearchengine.pdf.extractor.PdfTextExtractor
import com.krystiankowalik.pdfsearchengine.pdf.extractor.PdfTextExtractorImpl

class RegexPdfSearcherImpl(val pdfFilePath: String) : RegexPdfSearcher {

    private val pdfTextExtractor: PdfTextExtractor = PdfTextExtractorImpl(pdfFilePath)

    private val pdfText = pdfTextExtractor.getText()

    override fun containsRegex(regex: Regex) :Boolean{
        val dotMatchesAllRegex = Regex(regex.pattern,RegexOption.DOT_MATCHES_ALL)
        return pdfText.contains(dotMatchesAllRegex)
    }


}
