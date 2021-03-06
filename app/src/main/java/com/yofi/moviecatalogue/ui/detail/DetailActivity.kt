package com.yofi.moviecatalogue.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.yofi.moviecatalogue.BuildConfig
import com.yofi.moviecatalogue.data.DataEntity
import com.yofi.moviecatalogue.data.source.response.ItemMovie
import com.yofi.moviecatalogue.data.source.response.ItemTvShow
import com.yofi.moviecatalogue.databinding.ActivityDetailBinding
import com.yofi.moviecatalogue.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var result: DataEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail Movie"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewModel = ViewModelProvider(
            this@DetailActivity,
            ViewModelFactory.getInstance()
        )[DetailViewModel::class.java]

        val id = intent.getIntExtra(EXTRA_ID,0)
        val type = intent.getStringExtra(EXTRA_TYPE)

        if (type.equals("MOVIE")) {
            viewModel.getDetailMovieById(id).observe(this,{
                showDetailMovie(it)
            })
        } else if (type.equals("TVSHOW")) {
            viewModel.getDetailTvShowById(id).observe(this,{
                showDetailTvShow(it)
            })
        }


    }

    private fun showDetailMovie(result: ItemMovie) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(BuildConfig.IMAGE_URL + result.posterPath)
                .into(imgPoster)
            tvName.text = result.title + " ("+ result.releaseDate.substring(0,4) + ")"
            tvRating.text = result.voteAverage.toString()
            tvDesc.text = result.overview
        }
    }

    private fun showDetailTvShow(result: ItemTvShow) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(BuildConfig.IMAGE_URL + result.posterPath)
                .into(imgPoster)
            tvName.text = result.originalName + " ("+ result.firstAirDate.substring(0,4) + ")"
            tvRating.text = result.voteAverage.toString()
            tvDesc.text = result.overview
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TYPE = "extra_type"
    }
}