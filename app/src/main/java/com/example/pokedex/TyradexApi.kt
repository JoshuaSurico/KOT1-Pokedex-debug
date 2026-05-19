package com.example.pokedex

import retrofit2.http.GET
import retrofit2.http.Path

interface TyradexApi {

    @GET("api/v1/pokemon/{idOrName}")
    suspend fun getPokemon(@Path("idOrName") idOrName: String): Pokemon

}