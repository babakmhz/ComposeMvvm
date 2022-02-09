package com.babakmhz.composemvvm.data

import com.babakmhz.composemvvm.data.db.DbHelper
import com.babakmhz.composemvvm.data.db.model.Photo
import com.babakmhz.composemvvm.data.network.ApiService
import com.babakmhz.composemvvm.data.util.PhotoItemToPhotoModelMapper
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dbHelper: DbHelper,
    private val photoItemToPhotoModelMapper: PhotoItemToPhotoModelMapper
) : RepositoryHelper {

    override suspend fun getPhotosFromRemoteSource(): List<Photo> {
        val photos = photoItemToPhotoModelMapper.mapToDomainModelList(apiService.getPhotos())
        dbHelper.insertAllPhotos(photos)
        return photos
    }

    override suspend fun getPhotosFromLocalSource(): List<Photo> {
        return dbHelper.getAllPhotos()
    }


}