package com.krystiankowalik.pdfsearchengine

class IndexItem(val id: Long,
                val title: String,
                val content: String
) {
    companion object {
        val CONTENT = "content"
        val TITLE = "title"
        val ID = "id"
    }

}