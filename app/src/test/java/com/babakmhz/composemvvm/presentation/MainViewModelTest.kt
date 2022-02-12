package com.babakmhz.composemvvm.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.babakmhz.composemvvm.data.RepositoryHelper
import com.babakmhz.composemvvm.data.db.model.Photo
import com.babakmhz.composemvvm.utils.CoroutineTestRule
import io.mockk.coEvery
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {


    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule(coroutineDispatcher)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewModel: MainViewModel by lazy {
        MainViewModel(repositoryHelper)
    }

    private lateinit var repositoryHelper: RepositoryHelper


    @Before
    fun setup() {
        repositoryHelper = spyk()
    }

    @Test
    fun `test if no cashed photos, the livedata should remain in IDLE state`() = runBlockingTest {
        //given
        coEvery { repositoryHelper.getPhotosFromLocalSource() } returns arrayListOf()
        //when
        viewModel.photosState.observeForever {  }
        //then
        assertNotNull(viewModel.photosState)
        assertTrue(viewModel.photosState.value.isNullOrEmpty())
    }

    @Test
    fun `test if there are cashed photos, the livedata should be in success state`() =
        runBlockingTest {
            //given
            val data = listOf(Photo(0))
            coEvery { repositoryHelper.getPhotosFromLocalSource() } returns data
            //when
            viewModel.photosState.observeForever {
                println("photos state $it")
            }
            //then
            assertNotNull(viewModel.photosState)
            assertTrue(viewModel.photosState.value?.isNotEmpty() == true)
        }

    @Test
    fun `test data cannot be fetched from remote source should change live data state to Error`() =
        runBlockingTest {
            // given
            val returnError = Throwable()
            coEvery { repositoryHelper.getPhotosFromLocalSource() } returns arrayListOf()
            coEvery { repositoryHelper.getPhotosFromRemoteSource() } throws (returnError)

            // when
            viewModel.errorState.observeForever {}

            // then
            assertNotNull(viewModel.errorState.value)
            assertTrue(viewModel.errorState.value!=null)
        }

    @Test
    fun `test data fetched from remote source should change live data state to loading then success`() =
        coroutineDispatcher.runBlockingTest {
            // given
            val delayTime = 1000L
            val data = listOf(Photo(0))
            coEvery { repositoryHelper.getPhotosFromLocalSource() } returns arrayListOf()
            coEvery { repositoryHelper.getPhotosFromRemoteSource() } coAnswers {
                delay(delayTime)
                data
            }

            // when
            viewModel.photosState.observeForever {}
            viewModel.loadingState.observeForever {}

            // then
            assertEquals(viewModel.loadingState.value, true)
            advanceTimeBy(delayTime)
            assertTrue(viewModel.photosState.value!!.isNotEmpty())
        }
}