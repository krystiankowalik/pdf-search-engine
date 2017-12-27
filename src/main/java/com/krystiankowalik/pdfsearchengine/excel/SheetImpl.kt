package com.krystiankowalik.pdfsearchengine.excel

import org.apache.poi.ss.usermodel.Sheet

class SheetImpl(override val apacheSheet: Sheet) : com.krystiankowalik.pdfsearchengine.excel.Sheet(apacheSheet) {

}