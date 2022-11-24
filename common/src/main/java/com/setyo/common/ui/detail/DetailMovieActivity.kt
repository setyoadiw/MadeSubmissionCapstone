package com.setyo.common.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.setyo.common.R
import com.setyo.common.databinding.ActivityDetailMovieBinding
import com.setyo.common.domain.model.MovieData
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    private val viewModel by viewModel<DetailMovieViewModel>()

    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent?.getParcelableExtra<MovieData>("data")

        viewModel.isFavoriteMovie(data?.id!!)
        viewModel.getDetailMovies(data.id.toString())
        viewModel.getReviews(data.id.toString())

        Glide.with(binding.root.context)
            .load("https://image.tmdb.org/t/p/original/${data.posterPath}")
            .into(binding.ivHeader)
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = data.originalTitle
        binding.tvContent.text = data.overview
        binding.fab.setOnClickListener {
            viewModel.setFavorite(data, viewModel.isFavorite.value.not())
            showToast()
        }
        reviewAdapter = ReviewAdapter()
        binding.rvReview.adapter = reviewAdapter

        setupObservable()
    }

    private fun showToast() {
        Snackbar.make( binding.root,
            if(viewModel.isFavorite.value.not())"Added to favorite!" else "Remove from favorite.", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private fun setupObservable() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isFavorite.collect { isFavorite ->
                    if (isFavorite){
                        binding.fab.setImageDrawable(ContextCompat.getDrawable(this@DetailMovieActivity, R.drawable.ic_favorite))
                    } else {
                        binding.fab.setImageDrawable(ContextCompat.getDrawable(this@DetailMovieActivity, R.drawable.ic_unfavorite))
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailMovie.collect { data ->
                    binding.tvReviewValue.text = data.data?.genres?.joinToString { it?.name!! }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.reviews.collect { data ->
                    data.data?.let { review ->
                        if (review.isEmpty()) {
                            binding.tvReviewEmpty.visibility = View.VISIBLE
                        } else {
                            binding.tvReviewEmpty.visibility = View.GONE
                            reviewAdapter.submitList(review)
                        }
                    }
                }
            }
        }
    }
}