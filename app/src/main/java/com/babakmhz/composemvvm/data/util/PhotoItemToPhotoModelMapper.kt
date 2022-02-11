package com.babakmhz.composemvvm.data.util

import com.babakmhz.composemvvm.data.db.model.Photo
import com.babakmhz.composemvvm.data.network.response.PhotoItemResponse

class PhotoItemToPhotoModelMapper : Mapper<PhotoItemResponse, Photo> {
    override fun mapToDomainModel(model: PhotoItemResponse): Photo {
        return Photo(
            model.albumId,
            model.id.toLong(),
            "${model.thumbnailUrl}.png",
            model.title,
            "${model.thumbnailUrl}.png",
        )
    }

    override fun mapFromDomainModel(domainModel: Photo): PhotoItemResponse {
        return PhotoItemResponse(
            domainModel.albumId,
            domainModel.id.toInt(),
            domainModel.thumbnailUrl.replaceAfterLast(".png",""),
            domainModel.title,
            domainModel.url.replaceAfterLast(".png","")
        )
    }

    fun mapToDomainModelList(model: List<PhotoItemResponse>): List<Photo> {
        return model.map {
            mapToDomainModel(it)
        }
    }
}