package com.example.data.repository

import com.example.data.api.ApiService
import com.example.data.model.CarSearchResponseItem
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject

@ActivityRetainedScoped
class CarsRepository @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun getCars(): List<CarSearchResponseItem> = apiService.getCars()
}