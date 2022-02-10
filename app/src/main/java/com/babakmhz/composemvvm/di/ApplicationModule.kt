package com.babakmhz.composemvvm.di

import android.content.Context
import com.babakmhz.composemvvm.BuildConfig
import com.babakmhz.composemvvm.data.RepositoryHelper
import com.babakmhz.composemvvm.data.RepositoryImpl
import com.babakmhz.composemvvm.data.db.DbHelper
import com.babakmhz.composemvvm.data.db.DbImpl
import com.babakmhz.composemvvm.data.db.model.MyObjectBox
import com.babakmhz.composemvvm.data.network.ApiService
import com.babakmhz.composemvvm.data.util.PhotoItemToPhotoModelMapper
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.objectbox.BoxStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRepositoryHelper(repositoryImpl: RepositoryImpl) : RepositoryHelper = repositoryImpl

    @Provides
    @Singleton
    fun provideDbHelper(dbImpl: DbImpl): DbHelper = dbImpl

    @Provides
    @Singleton
    fun providePhotoResponseToModelMapper(): PhotoItemToPhotoModelMapper =
        PhotoItemToPhotoModelMapper()


    @Provides
    @Singleton
    fun provideBoxStore(@ApplicationContext context: Context): BoxStore =
        MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .name("ComposeMVVM_V1")
            .build()

}