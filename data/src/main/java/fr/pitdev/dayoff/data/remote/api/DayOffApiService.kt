package fr.pitdev.dayoff.data.remote.api

import fr.pitdev.dayoff.data.dtos.DayOffDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DayOffApiService {

    @GET("/jours-feries/{zone}/{year}.json")
    suspend fun getAll(@Path("zone") zone: String, @Path("year") year: Int? = null): Response<DayOffDto>

}