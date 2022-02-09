package com.babakmhz.composemvvm.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakmhz.composemvvm.data.RepositoryHelper
import com.babakmhz.composemvvm.data.db.model.Photo
import com.babakmhz.composemvvm.utils.MainUiState
import com.babakmhz.composemvvm.utils.launchWithException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryHelper: RepositoryHelper
) : ViewModel() {

    private val _photosLiveData = MutableLiveData<MainUiState<List<Photo>>>()
    val photosLiveData: LiveData<MainUiState<List<Photo>>> = _photosLiveData

    init {
        viewModelScope.launch {
            val cachedPhotos = repositoryHelper.getPhotosFromLocalSource()
            if (cachedPhotos.isNotEmpty())
                _photosLiveData.postValue(MainUiState.Success(cachedPhotos))
        }
    }

    fun fetchPhotos() = viewModelScope.launchWithException(_photosLiveData) {
        _photosLiveData.postValue(MainUiState.Loading)
        val apiResponse = repositoryHelper.getPhotosFromRemoteSource()
    }
}