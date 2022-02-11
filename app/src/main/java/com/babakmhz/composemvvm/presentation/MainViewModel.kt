package com.babakmhz.composemvvm.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakmhz.composemvvm.data.RepositoryHelper
import com.babakmhz.composemvvm.data.db.model.Photo
import com.babakmhz.composemvvm.utils.launchWithException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryHelper: RepositoryHelper
) : ViewModel() {

    private var _photosState: MutableLiveData<List<Photo>> = MutableLiveData()
    val photosState: LiveData<List<Photo>> = _photosState
    private var _loadingState = MutableLiveData(false)
    val loadingState: LiveData<Boolean> = _loadingState
    private var _errorState: MutableLiveData<Throwable> = MutableLiveData()
    val errorState: LiveData<Throwable> = _errorState

    init {
        viewModelScope.launchWithException(_errorState) {
            val cachedPhotos = repositoryHelper.getPhotosFromLocalSource()
            if (cachedPhotos.isNotEmpty())
                _photosState.postValue(cachedPhotos)

            fetchPhotos()
        }
    }

    private fun fetchPhotos() = viewModelScope.launchWithException(_errorState) {
        _loadingState.postValue(true)
        val apiResponse = repositoryHelper.getPhotosFromRemoteSource()
        _photosState.postValue(apiResponse)
        _loadingState.postValue(false)
    }
}