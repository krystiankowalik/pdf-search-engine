package com.krystiankowalik.pdfsearchengine.model

data class PdfQuery(var description: String,
                    var searchedText: Regex,
                    var hit: String
) {
    init {
        searchedText = Regex(searchedText.pattern, RegexOption.DOT_MATCHES_ALL)
    }

}


