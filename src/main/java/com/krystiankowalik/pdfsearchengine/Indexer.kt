package com.krystiankowalik.pdfsearchengine

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.index.Term
import org.apache.lucene.store.FSDirectory
import org.apache.lucene.util.Version
import java.io.File

class Indexer(indexDir: String) {

    private val indexWriter = IndexWriter(FSDirectory.open(File(indexDir)),
            IndexWriterConfig(Version.LUCENE_36, StandardAnalyzer(Version.LUCENE_36)))

    fun index(indexItem: IndexItem) {
        indexWriter.deleteDocuments(Term(IndexItem.ID, indexItem.id.toString()))

        val document = Document()

        document.add(Field(IndexItem.ID, indexItem.id.toString(), Field.Store.YES, Field.Index.NOT_ANALYZED))
        document.add(Field(IndexItem.TITLE, indexItem.title, Field.Store.YES, Field.Index.ANALYZED))
        document.add(Field(IndexItem.CONTENT, indexItem.content, Field.Store.YES, Field.Index.ANALYZED))

        indexWriter.addDocument(document)
    }

    fun close() {
        indexWriter.close()
    }
}