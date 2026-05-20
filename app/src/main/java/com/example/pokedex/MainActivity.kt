package com.example.pokedex

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        private const val MAX_POKEMON_ID = 1010
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayPokemon(currentId)

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

        binding.btnPrecedent.setOnClickListener {
            if (currentId > 1) {
                currentId--
                displayPokemon(currentId)
            }
        }

        binding.btnSuivant.setOnClickListener {
            if (currentId < MAX_POKEMON_ID) {
                currentId++
                displayPokemon(currentId)
            }
        }

        binding.btnRandom.setOnClickListener {
            currentId = (1..MAX_POKEMON_ID).random()
            displayPokemon(currentId)
        }
    }

    private var currentPokemon: Pokemon? = null
    private var currentId = 1

    private fun displayPokemon(id: Int) {

        lifecycleScope.launch {
            try {
                val pokemon = RetrofitClient.api.getPokemon(id.toString())
                currentPokemon = pokemon
                Glide.with(this@MainActivity)
                    .load(pokemon.sprites?.regular)
                    .into(binding.imgSprite)
                binding.pokemonName.text = pokemon.name.fr
                binding.generation.text = "Gén. " + (pokemon.generation)
                binding.category.text = pokemon.category.ifBlank { "Inconnue" }
                binding.hp.text = "PV : " + (pokemon.stats?.hp ?: "?")
                binding.atk.text = "Atk: " + (pokemon.stats?.atk ?: "?")
                binding.def.text = "Def: " + (pokemon.stats?.def ?: "?")
                binding.height.text = "Taille : " + (pokemon.height ?: "?")
                binding.weight.text = "Poids : " + (pokemon.weight ?: "?")
            } catch (e: Exception) {
                binding.pokemonName.text = "Erreur de chargement"
                e.printStackTrace()
            }
        }
    }
}