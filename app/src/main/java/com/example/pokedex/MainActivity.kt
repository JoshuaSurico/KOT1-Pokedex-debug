package com.example.pokedex

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pokedex.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayPokemon(1)
    }

    private fun displayPokemon(id: Int) {
        lifecycleScope.launch {
            try {
                val pokemon = RetrofitClient.api.getPokemon(id.toString())
                binding.pokemonName.text = pokemon.name.fr
            } catch (e: Exception) {
                binding.pokemonName.text = "Erreur de chargement"
                e.printStackTrace()
            }
        }
    }
}