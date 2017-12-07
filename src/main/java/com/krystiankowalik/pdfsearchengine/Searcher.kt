package com.krystiankowalik.pdfsearchengine

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.index.IndexReader
import org.apache.lucene.queryParser.QueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.FSDirectory
import org.apache.lucene.util.Version
import java.io.File

class Searcher(indexDir: String) {

    private val searcher: IndexSearcher = IndexSearcher(IndexReader.open(FSDirectory.open(File(indexDir))))
    private val analyzer: StandardAnalyzer = StandardAnalyzer(Version.LUCENE_36)
    private val contentQueryParser: QueryParser = QueryParser(Version.LUCENE_36, IndexItem.CONTENT, analyzer)

    fun findByContent(stringQuery: String, resultCount: Int): Int {
        val query = contentQueryParser.parse(stringQuery)
        val queryResults = searcher.search(query, resultCount).scoreDocs
        return if (queryResults.isNotEmpty()) {
            1
        } else {
            0
        }
    }

    fun close() {
        searcher.close()
    }
}