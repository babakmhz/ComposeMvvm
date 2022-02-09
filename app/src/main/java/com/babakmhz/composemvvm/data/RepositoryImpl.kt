package com.babakmhz.composemvvm.data

import com.babakmhz.composemvvm.data.network.ApiService
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : RepositoryHelper {


}