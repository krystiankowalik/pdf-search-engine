package com.krystiankowalik.pdfsearchengine.io

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors
import java.util.stream.Stream

class RecursiveFileLister(val baseDirectory: String) {

    fun listAll(): List<File> =
            getRecursiveFileStream(baseDirectory)
                    .collect(Collectors.toList())

    fun listAll(extension: String): List<File> =
            getRecursiveFileStream(baseDirectory)
                    .filter({ f -> f.toString().trim().endsWith(extension) })
                    .collect(Collectors.toList())

    private fun getRecursiveFileStream(baseDirectory: String): Stream<File> =
            Files.walk(Paths.get(baseDirectory))
                    .map { it.toFile() }
                    .filter(File::isFile)


}