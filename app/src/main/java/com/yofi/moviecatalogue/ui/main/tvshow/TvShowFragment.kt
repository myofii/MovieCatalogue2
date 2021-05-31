package com.yofi.moviecatalogue.ui.main.tvshow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yofi.moviecatalogue.R
import com.yofi.moviecatalogue.databinding.FragmentListTvshowBinding
import com.yofi.moviecatalogue.ui.main.MainViewModel
import com.yofi.moviecatalogue.viewmodel.ViewModelFactory

class TvShowFragment: Fragment(R.layout.fragment_list_tvshow) {
    private var _binding : FragmentListTvshowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: TvShowAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListTvshowBinding.bind(view)

        adapter = TvShowAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvTvShow.setHasFixedSize(true)
            rvTvShow.layoutManager = LinearLayoutManager(activity)
            rvTvShow.adapter = adapter
        }

        if (activity != null) {

            val viewModelFactory = ViewModelFactory.getInstance()
            activity?.let {
                viewModel = ViewModelProvider(
                    it,
                    viewModelFactory
                )[MainViewModel::class.java]
            }

            viewModel.getListTvShow().observe(viewLifecycleOwner, { listTvShow ->
                binding.rvTvShow.adapter?.let { adapter ->
                    when (adapter) {
                        is TvShowAdapter -> adapter.setListDataTvShow(listTvShow)
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