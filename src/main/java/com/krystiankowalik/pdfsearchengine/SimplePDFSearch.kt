package com.krystiankowalik.pdfsearchengine

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File

class SimplePDFSearch {


    companion object {
        val INDEX_DIR = "/home/wd43/Downloads/testdskjfd/index/"
        val DEFAULT_RESULT_SIZE = 100
    }

    fun run() {
        val pdfFile = File("/home/wd43/Downloads/testdskjfd/kp.pdf")
        val pdfIndexItem = index(pdfFile)

        val indexer = Indexer(INDEX_DIR)
        indexer.index(pdfIndexItem)
        indexer.close()

        val searcher = Searcher(INDEX_DIR)
        val result = searcher.findByContent("KoDeKs", DEFAULT_RESULT_SIZE)
        printResult(result)
        searcher.close()
    }

    fun index(file: File): IndexItem {
        val pdDocument = PDDocument.load(file)
        val content = PDFTextStripper().getText(pdDocument)
        pdDocument.close()
        return IndexItem(file.name.hashCode().toLong(), file.name, content)
    }

    fun printResult(result: Int) {
        if (result == 1) {
            print("Contains")
        } else {
            println("Doesn't containt keyword")
        }
    }


}