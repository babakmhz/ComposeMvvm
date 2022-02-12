package com.babakmhz.composemvvm.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakmhz.composemvvm.data.RepositoryHelper
import com.babakmhz.composemvvm.data.db.model.Photo
import com.babakmhz.composemvvm.utils.launchWithException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryHelper: RepositoryHelper
) : ViewModel() {

    // using mutableState is also possible instead of livedata in case of composable UIs

    private var _photosState = MutableLiveData<List<Photo>>()
    val photosState: LiveData<List<Photo>> = _photosState

    private var _loadingState = MutableLiveData(false)
    val loadingState: LiveData<Boolean> = _loadingState

    private var _errorState: MutableLiveData<Throwable?> = MutableLiveData(null)
    val errorState: LiveData<Throwable?> = _errorState

    init {
        viewModelScope.launchWithException(_errorState, _loadingState) {
            awaitAll(
                async { fetchPhotos() },
                async {
                    val cachedPhotos =
                        repositoryHelper.getPhotosFromLocalSource()
                    if (cachedPhotos.isNotEmpty())
                        _photosState.postValue(cachedPhotos)
                }
            )
        }
    }


    private suspend fun fetchPhotos() {
        _loadingState.postValue(true)
        val apiResponse = repositoryHelper.getPhotosFromRemoteSource()
        _photosState.postValue(apiResponse)
        _loadingState.postValue(false)
    }

    fun reFetchPhotos() = viewModelScope.launchWithException(_errorState, _loadingState) {
        fetchPhotos()
    }
}