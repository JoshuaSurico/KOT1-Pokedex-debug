package com.example.pokedex

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("pokedex_id") val pokedexId: Int,
    val generation: Int,
    val category: String,
    val name: PokemonName,
    val sprites: PokemonSprites,
    val types: List<PokemonType>?
)

data class PokemonName(
    val fr: String,
    val en: String,
    val jp: String
)


data class PokemonSprites(
    val regular: String,
    val shiny: String
)

data class PokemonType(
    val name: String,
    val image: String
)
