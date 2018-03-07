package com.krystiankowalik.pdfsearchengine.util

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Sheet

fun Sheet.mutableCell(rowNum: Int, columnNum: Int): Cell {

    if (this.getRow(rowNum) == null) {
        this.createRow(rowNum)
    }
    val row = this.getRow(rowNum)

    if (row.getCell(columnNum) == null) {
        row.createCell(columnNum)
    }
    return row.getCell(columnNum)

}