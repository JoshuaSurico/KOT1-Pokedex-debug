package com.example.pokedex

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
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

        binding.btnFr.setOnClickListener {
            binding.pokemonName.text = currentPokemon?.name?.fr
        }

        binding.btnEn.setOnClickListener {
            binding.pokemonName.text = currentPokemon?.name?.en
        }

        binding.btnJp.setOnClickListener {
            binding.pokemonName.text = currentPokemon?.name?.jp
        }

        binding.switchShiny.setOnCheckedChangeListener { _, isChecked ->
            val url = if (isChecked) {
                currentPokemon?.sprites?.shiny
            } else {
                currentPokemon?.sprites?.regular
            }
            Glide.with(this)
                .load(url)
                .into(binding.imgSprite)
        }
    }

    private var currentPokemon: Pokemon? = null

    private fun displayPokemon(id: Int) {

        lifecycleScope.launch {
            try {
                val pokemon = RetrofitClient.api.getPokemon(id.toString())
                currentPokemon = pokemon
                Glide.with(this@MainActivity)
                    .load(pokemon.sprites.regular)
                    .into(binding.imgSprite)
                binding.pokemonName.text = pokemon.name.fr
                binding.generation.text = "Gén. " + (pokemon.generation)
                binding.category.text = pokemon.category.ifBlank { "Inconnue" }
                binding.hp.text = "PV : " + (pokemon.stats?.hp ?: "?")
                binding.atk.text = "Atk: " + (pokemon.stats?.atk ?: "?")
                binding.def.text = "Def: " + (pokemon.stats?.def ?: "?")
            } catch (e: Exception) {
                binding.pokemonName.text = "Erreur de chargement"
                e.printStackTrace()
            }
        }
    }
}