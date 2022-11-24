package com.setyo.made.ui.popular

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
import com.setyo.common.utils.asFlow
import com.setyo.made.databinding.FragmentPopularBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class PopularFragment : BaseFragment() {

    private var _binding: FragmentPopularBinding? = null
    private val viewModel by viewModel<PopoularViewModel>()

    private lateinit var movieAdapter: MovieAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        movieAdapter = MovieAdapter(::onItemClick)
        binding.rvMovie.adapter = movieAdapter
        viewModel.getMovies()

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

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun setupObservable() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect { movies ->
                    when (movies) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            movieAdapter.submitList(movies.data)
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.edSearch.asFlow()
                    .debounce(400)
                    .collect {
                        if (it.isEmpty()) viewModel.getMovies()
                        else viewModel.searchMovies(it.trim())
                    }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}