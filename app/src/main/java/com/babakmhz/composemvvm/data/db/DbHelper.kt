package com.babakmhz.composemvvm.data.db

import com.babakmhz.composemvvm.data.db.model.Photo

interface DbHelper {
    suspend fun insertAllPhotos(photos: List<Photo>)
    suspend fun getAllPhotos():List<Photo>
}