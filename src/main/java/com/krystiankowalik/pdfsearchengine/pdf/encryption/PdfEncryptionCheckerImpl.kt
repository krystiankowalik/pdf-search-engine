package com.krystiankowalik.pdfsearchengine.pdf.encryption

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException
import java.io.File


class PdfEncryptionCheckerImpl(val pdfPath: File) : PdfEncryptionChecker {

    override fun isEncrypted(): Boolean {
        var encrypted = false
        try {
            val doc = PDDocument.load(pdfPath)
            if (doc.isEncrypted)
                encrypted = true
            doc.close()
        } catch (e: InvalidPasswordException) {
            encrypted = true
        }

        return encrypted
    }
}