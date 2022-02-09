package com.babakmhz.composemvvm.data

import com.babakmhz.composemvvm.data.db.model.Photo


interface RepositoryHelper {

    suspend fun getPhotosFromRemoteSource(): List<Photo>

    suspend fun getPhotosFromLocalSource():List<Photo>
}