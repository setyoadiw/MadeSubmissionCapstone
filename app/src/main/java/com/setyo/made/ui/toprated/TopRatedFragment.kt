package com.setyo.made.ui.toprated

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.setyo.common.data.Resource
import com.setyo.common.domain.model.MovieData
import com.setyo.common.ui.BaseFragment
import com.setyo.made.databinding.FragmentTopRatedBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopRatedFragment : BaseFragment() {

    private var _binding: FragmentTopRatedBinding? = null
    private val binding get() = _binding!!

    private lateinit var topMovieAdapter: TopMovieAdapter

    private val viewModel by viewModel<TopRatedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        topMovieAdapter = TopMovieAdapter(::onItemClick)
        binding.rvMovie.adapter = topMovieAdapter
        viewModel.getTopMovies()

    }

    private fun onItemClick(movieData: MovieData) {
        Intent().setClassName(
            requireContext().packageName,
            "com.setyo.common.ui.detail.DetailMovieActivity"
        ).also { intent ->
            intent.putExtra("data", movieData)
            startActivity(intent)
        }
    }


    override fun setupObservable() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect { movies ->
                    when (movies) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            topMovieAdapter.submitList(movies.data)
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}