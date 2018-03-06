package com.krystiankowalik.pdfsearchengine.model.dao

import com.krystiankowalik.pdfsearchengine.model.SearchedFile

interface Dao<T> {

    fun getAll(): List<T>

    fun get(index: Int): T

    fun update(index: Int, element: T)

    fun delete(index: Int)

    fun deleteAll()

    fun size():Int

    fun insert(element: T)

    fun insertAll(elements: List<SearchedFile>)
}