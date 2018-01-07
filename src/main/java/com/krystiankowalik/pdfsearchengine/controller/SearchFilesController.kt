package com.krystiankowalik.pdfsearchengine.controller

import com.krystiankowalik.pdfsearchengine.io.FileLister
import com.krystiankowalik.pdfsearchengine.io.RecursiveFileLister
import tornadofx.Controller
import tornadofx.runAsyncWithProgress
import java.awt.Desktop
import java.awt.SystemColor.desktop
import java.io.File

class SearchFilesController : Controller() {
    private lateinit var fileLister: FileLister


    fun listFiles(basePath: String): List<String> {
        fileLister = RecursiveFileLister(basePath)
        return fileLister.list("pdf").map { it.toString() }
    }




}