package com.babakmhz.composemvvm.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.babakmhz.composemvvm.data.RepositoryHelper
import com.babakmhz.composemvvm.data.db.model.Photo
import com.babakmhz.composemvvm.utils.CoroutineTestRule
import com.babakmhz.composemvvm.utils.MainUiState
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
        assertNull(viewModel.photosState)
    }

    @Test
    fun `test if there are cashed photos, the livedata should be in success state`() =
        coroutineTestRule.coroutineScope.runBlockingTest {
            //given
            val data = listOf(Photo(0))
            coEvery { repositoryHelper.getPhotosFromLocalSource() } returns data
            //when
            val viewModel = MainViewModel(repositoryHelper)
            viewModel.photosState.observeForever {
                println("state of live data $it")
            }
            //then
            assertNotNull(viewModel.photosState)
            assertTrue(viewModel.photosState.value!!.isNotEmpty())
        }

//    @Test
//    fun `test data cannot be fetched from remote source should change live data state to Error`() =
//        runBlockingTest {
//            // given
//            val returnError = Throwable()
//            coEvery { repositoryHelper.getPhotosFromLocalSource() } returns arrayListOf()
//            coEvery { repositoryHelper.getPhotosFromRemoteSource() } throws (returnError)
//            viewModel.photosLiveData.observeForever {}
//
//            // when
//            viewModel.fetchPhotos()
//
//            // then
//            assertNotNull(viewModel.photosLiveData.value)
//            assertEquals(viewModel.photosLiveData.value, MainUiState.Error(returnError))
//        }
//
//    @Test
//    fun `test data fetched from remote source should change live data state to loading then success`() =
//        coroutineDispatcher.runBlockingTest {
//            // given
//            val delayTime = 1000L
//            val data = listOf(Photo(0))
//            coEvery { repositoryHelper.getPhotosFromLocalSource() } returns arrayListOf()
//            coEvery { repositoryHelper.getPhotosFromRemoteSource() } coAnswers {
//                delay(delayTime)
//                data
//            }
//
//            viewModel.photosLiveData.observeForever {}
//
//            // when
//            viewModel.fetchPhotos()
//
//            // then
//            assertNotNull(viewModel.photosLiveData.value)
//            assertEquals(viewModel.photosLiveData.value, MainUiState.Loading)
//            advanceTimeBy(delayTime)
//            assertEquals(viewModel.photosLiveData.value, MainUiState.Success(data))
//        }
}