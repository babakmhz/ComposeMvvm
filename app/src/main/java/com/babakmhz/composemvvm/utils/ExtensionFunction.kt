package com.babakmhz.composemvvm.utils

import android.view.View
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun View?.toVisible() {
    if (this?.visibility != View.VISIBLE)
        this?.visibility = View.VISIBLE
}

fun View?.toGone() {
    if (this?.visibility != View.GONE)
        this?.visibility = View.GONE
}

fun View?.toInvisible() {
    if (this?.visibility != View.INVISIBLE)
        this?.visibility = View.INVISIBLE
}


fun String?.validString() = this != null && this.isNotEmpty()


fun <T : Any> CoroutineScope.launchWithException(
    livedata: MutableLiveData<MainUiState<T>>,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(CoroutineExceptionHandler { _, throwable ->
        livedata.postValue(MainUiState.Error(throwable))
    }, block = block)
}


