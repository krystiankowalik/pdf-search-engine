package com.krystiankowalik.pdfsearchengine.util

inline fun String.whenNotEmpty(f:()->Unit) {
    if(!this.isEmpty()){
        f()
    }
}