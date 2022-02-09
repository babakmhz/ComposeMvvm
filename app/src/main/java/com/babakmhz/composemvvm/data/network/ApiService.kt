package com.babakmhz.composemvvm.data.network

import com.babakmhz.composemvvm.data.network.response.PhotoItemResponse
import retrofit2.http.GET

interface ApiService {

    @GET(PHOTOS)
    suspend fun getPhotos():List<PhotoItemResponse>

}