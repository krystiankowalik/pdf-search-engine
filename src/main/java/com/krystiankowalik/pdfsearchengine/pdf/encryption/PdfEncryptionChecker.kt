package com.krystiankowalik.pdfsearchengine.pdf.encryption

interface PdfEncryptionChecker {

    fun isEncrypted(): Boolean
}