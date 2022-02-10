package com.babakmhz.composemvvm.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutineTestRule(private val coroutineDispatcher: CoroutineDispatcher)
    :TestWatcher(),TestCoroutineScope by TestCoroutineScope(coroutineDispatcher){

    val coroutineScope :TestCoroutineScope get() = this

    override fun starting(description: Description?) {
        super.starting(description)
        advanceUntilIdle()
        Dispatchers.setMain(coroutineDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}