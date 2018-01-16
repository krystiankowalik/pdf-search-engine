package com.krystiankowalik.pdfsearchengine.model.dao

interface Dao<T> {

    fun getAll(): List<T>

    fun get(index: Int): T

    fun update(index: Int, element: T)

    fun delete(index: Int)

    fun insert(element: T)

}