package com.williamtan.cat.data

import com.williamtan.cat.data.model.BreedModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {
    @GET("v1/breeds")
    suspend fun searchByBreed(
        @Query("breed_ids", encoded = true) breedIds: String?,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): List<BreedModel>

    @GET("v1/breeds/search")
    suspend fun searchByName(
        @Query("q") name: String
    ): List<BreedModel>
}