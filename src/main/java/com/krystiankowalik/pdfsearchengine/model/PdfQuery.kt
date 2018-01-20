package com.krystiankowalik.pdfsearchengine.model

class PdfQuery(var description: String,
               searchedText: String,
               var hit: String
) {
    var searchedText: Regex = Regex(searchedText, setOf(RegexOption.MULTILINE, RegexOption.DOT_MATCHES_ALL, RegexOption.IGNORE_CASE))

    override fun toString(): String {
        return "PdfQuery(description='$description', hit='$hit', searchedText=$searchedText)"
    }


}


