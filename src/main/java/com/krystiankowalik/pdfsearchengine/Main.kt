package com.krystiankowalik.pdfsearchengine

import com.krystiankowalik.pdfsearchengine.excel.getColumnAsList
import com.krystiankowalik.pdfsearchengine.excel.mutableCell
import com.krystiankowalik.pdfsearchengine.io.RecursiveFileLister
import com.krystiankowalik.pdfsearchengine.model.PdfQueryRow
import com.krystiankowalik.pdfsearchengine.pdf.PdfSearcher
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.ss.util.WorkbookUtil
import java.io.FileInputStream
import java.io.FileOutputStream
import org.apache.poi.ss.usermodel.DataFormatter


fun main(args: Array<String>) {
/*
    WorkbookFactory.create(FileInputStream("src/main/resources/content.xlsx")).use {
        val sheet = it.getSheetAt(0)

        sheet.com.krystiankowalik.pdfsearchengine.excel.mutableCell(20, 1).setCellValue("test")
        it.write(FileOutputStream("src/main/resources/content3.xlsx"))
    }

    WorkbookFactory.create(FileInputStream("src/main/resources/content.xlsx")).use {
        val sheet = it.getSheetAt(0)

        sheet.getColumnAsList(0).forEach(::println)

    }*/

    val formatter = DataFormatter()

    // 1. Read files from base path

    val allFiles = RecursiveFileLister("/home/wd43/Downloads/testdskjfd")
            .listAll("pdf")

    // 2. Write files to sheet
    val workbook = WorkbookFactory.create(FileInputStream("src/main/resources/content.xlsx"))
    val sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName("Files_Found"))

    for (i in 0 until allFiles.size) {
        sheet.mutableCell(i, 0).setCellValue(allFiles[i].toString())
    }


    // 3. Read queries from query sheet to memory
    val queries = mutableListOf<PdfQueryRow>()
    val querySheet = workbook.getSheet("Query")
    for (i in 0..querySheet.lastRowNum) {
        val row = querySheet.getRow(i)
        queries.add(row.rowNum, PdfQueryRow())

        (0..row.lastCellNum)
                .asSequence()
                .mapNotNull { row.getCell(it) }
                .forEach {
                    queries[row.rowNum]
                            .stringsToQuery
                            .add(formatter.formatCellValue(it))
                }


    }
    queries.forEach(::println)


// 4. Go through each file checking if it contains any of the strings -> write results to List<PdfQueryRow>
    allFiles.forEach({
        val pdfSearcher = PdfSearcher(it.toString())
        queries.forEach({
            if (pdfSearcher.containsAny(it.stringsToQuery)) {
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
    workbook.write(FileOutputStream("src/main/resources/content5.xlsx"))
    workbook.close()
}