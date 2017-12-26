package com.krystiankowalik.pdfsearchengine.io

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors
import java.util.stream.Stream

class RecursiveFileLister(override val baseDirectory: String) : FileLister(baseDirectory) {

    override fun list(): List<File> =
            getRecursiveFileStreamFromBaseDirectory()
                    .collect(Collectors.toList())

    override fun list(extension: String): List<File> =
            getRecursiveFileStreamFromBaseDirectory()
                    .filter({ f -> f.toString().trim().endsWith(extension) })
                    .collect(Collectors.toList())

    private fun getRecursiveFileStreamFromBaseDirectory(): Stream<File> =
            Files.walk(Paths.get(baseDirectory))
                    .map { it.toFile() }
                    .filter(File::isFile)


}