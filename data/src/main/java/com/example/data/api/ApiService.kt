package com.example.data.api

import com.example.data.model.CarSearchResponseItem
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("/")
    suspend fun getCars(): List<CarSearchResponseItem>
}
