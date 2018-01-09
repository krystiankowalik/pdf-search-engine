package com.krystiankowalik.pdfsearchengine.util


fun Any?.whenNotNull(f: () -> Unit) {
    if (this != null) {
        f()
    }
}