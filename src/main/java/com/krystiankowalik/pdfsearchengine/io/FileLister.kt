package com.krystiankowalik.pdfsearchengine.io

import java.io.File
import java.io.FileNotFoundException

abstract class FileLister(open val baseDirectory: String) {

    @Throws(java.nio.file.NoSuchFileException::class)
    abstract fun list(): List<File>

    @Throws(java.nio.file.NoSuchFileException::class)
    abstract fun list(extension: String): List<File>
}