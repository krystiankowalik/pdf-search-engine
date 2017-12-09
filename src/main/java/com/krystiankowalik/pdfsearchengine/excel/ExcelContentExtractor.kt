package com.krystiankowalik.pdfsearchengine.excel

import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Sheet


object FormatterProvider {
    val formatter = DataFormatter()
}

fun Sheet.getColumnAsList(columnIndex: Int): List<String> =
        this
                .map {
                    FormatterProvider.formatter
                            .formatCellValue(it
                                    .getCell(columnIndex))
                            .trim()
                }.toList()
