package com.example.photoweather.domain.usecase.getcars

import com.example.data.common.Resource
import com.example.data.model.CarSearchResponseItem
import com.example.data.repository.CarsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetCarsUseCase @Inject constructor(
    private val repository: CarsRepository,
    private val defaultDispatcher: CoroutineDispatcher
) {

    private var carList = listOf<CarSearchResponseItem>()

    suspend operator fun invoke(): Flow<Resource<List<CarSearchResponseItem>>> =
        flow<Resource<List<CarSearchResponseItem>>> {
            try {
                emit(Resource.loading())
                carList = repository.getCars()
                emit(Resource.success(carList))
            } catch (e: Throwable) {
                emit(Resource.error(e))
            }
        }.flowOn(defaultDispatcher)

}

