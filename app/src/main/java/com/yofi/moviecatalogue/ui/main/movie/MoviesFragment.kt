package com.yofi.moviecatalogue.ui.main.movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yofi.moviecatalogue.R
import com.yofi.moviecatalogue.databinding.FragmentListMovieBinding
import com.yofi.moviecatalogue.ui.main.ListAdapter
import com.yofi.moviecatalogue.ui.main.MainViewModel
import com.yofi.moviecatalogue.viewmodel.ViewModelFactory

class MoviesFragment: Fragment(R.layout.fragment_list_movie) {
    private var _binding : FragmentListMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListMovieBinding.bind(view)

        adapter = MovieAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvMovie.setHasFixedSize(true)
            rvMovie.layoutManager = LinearLayoutManager(activity)
            rvMovie.adapter = adapter
        }

        if (activity != null) {

            val viewModelFactory = ViewModelFactory.getInstance()
            activity?.let {
                viewModel = ViewModelProvider(
                    it,
                    viewModelFactory
                )[MainViewModel::class.java]
            }

            viewModel.getListMovie().observe(viewLifecycleOwner, { listMovie ->
                binding.rvMovie.adapter?.let { adapter ->
                    when (adapter) {
                        is MovieAdapter -> adapter.setListDataMovie(listMovie)
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}