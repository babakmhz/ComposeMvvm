package com.babakmhz.composemvvm.data.db

import com.babakmhz.composemvvm.data.db.model.Photo
import io.objectbox.Box
import io.objectbox.BoxStore
import javax.inject.Inject

class DbImpl @Inject constructor(boxStore: BoxStore) : DbHelper {

    private val boxStore: Box<Photo> by lazy {
        boxStore.boxFor(Photo::class.java)
    }

    override suspend fun insertAllPhotos(photos: List<Photo>) {
        boxStore.removeAll()
        boxStore.put(photos)
    }
    override suspend fun getAllPhotos(): List<Photo> = boxStore.all
}