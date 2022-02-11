package com.babakmhz.composemvvm.utils

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


fun String?.validString() = this != null && this.isNotEmpty()


fun CoroutineScope.launchWithException(
    livedata: MutableLiveData<Throwable?>,
    loading: MutableLiveData<Boolean>,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(CoroutineExceptionHandler { _, throwable ->
        livedata.postValue(throwable)
        loading.postValue(false)
    }, block = block)
}


