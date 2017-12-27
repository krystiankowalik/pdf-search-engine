package com.krystiankowalik.pdfsearchengine

import com.krystiankowalik.pdfsearchengine.excel.mutableCell
import com.krystiankowalik.pdfsearchengine.io.RecursiveFileLister
import com.krystiankowalik.pdfsearchengine.model.PdfQueryRow
import com.krystiankowalik.pdfsearchengine.pdf.searcher.RegexPdfSearcherImpl
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream
import java.io.FileOutputStream

class Control() {

    fun run(pdfSearchBaseFolder: String, inputFilePath: String, outputFilePath: String) {

        val formatter = DataFormatter()

        // 1. Read files from base path

        val allFiles = RecursiveFileLister(pdfSearchBaseFolder)
                .list("pdf")

        // 2. Write files to apacheSheet
        val workbook = WorkbookFactory.create(FileInputStream(inputFilePath))

        // 3. Read queries from query apacheSheet to memory
        val queries = mutableListOf<PdfQueryRow>()
        val querySheet = workbook.getSheet("Query")
        (0..querySheet.lastRowNum)
                .asSequence()
                .map { querySheet.getRow(it) }
                .forEach {
                    queries.add(it.rowNum, PdfQueryRow(Regex(formatter.formatCellValue(it.first())), mutableSetOf()))
                }
        queries.forEach(::println)


// 4. Go through each file checking if it contains any of the strings -> write results to List<PdfQueryRow>
        allFiles.forEach({
            val pdfSearcher = RegexPdfSearcherImpl(it.toString())
            queries.forEach({
                if (pdfSearcher.containsRegex(it.regex)) {
                    it.hits.add(pdfSearcher.pdfFilePath)
                }
            })
        })

        queries.forEach(::println)


// 5. Write the results from List<PdfQueryRow> to excel

        val resultStartColumn = querySheet.getRow(0).lastCellNum + 2
        for (i in 0 until queries.size) {
            val query = queries[i]
            for (j in 0 until query.hits.size)
                querySheet.mutableCell(i, resultStartColumn + j).setCellValue(query.hits.elementAt(j))
        }
        FileOutputStream(outputFilePath).use {
            workbook.write(it)
        }
        workbook.close()
    }
}