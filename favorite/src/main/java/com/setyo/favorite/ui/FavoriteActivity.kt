package com.setyo.favorite.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.setyo.common.domain.model.MovieData
import com.setyo.favorite.databinding.ActivityFavoriteBinding
import com.setyo.favorite.di.favoriteModule
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val viewModel by viewModel<FavoriteViewModel>()

    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadKoinModules(favoriteModule)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Favorite"

        initView()
        setupObservable()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoritesMovies()
    }

    private fun initView() {
        favoriteAdapter = FavoriteAdapter(::onItemClick)
        binding.rvMovie.adapter = favoriteAdapter
    }

    private fun onItemClick(movieData: MovieData) {
        Intent().setClassName(
            this.packageName,
            "com.setyo.common.ui.detail.DetailMovieActivity"
        ).also { intent ->
            intent.putExtra("data", movieData)
            startActivity(intent)
        }
    }

    private fun setupObservable() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favorites.collect { movies ->
                    favoriteAdapter.submitList(movies)
                }
            }
        }
    }

}