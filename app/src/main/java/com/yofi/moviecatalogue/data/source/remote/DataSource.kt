package com.yofi.moviecatalogue.data.source.remote

import androidx.lifecycle.LiveData
import com.yofi.moviecatalogue.data.source.response.ItemMovie
import com.yofi.moviecatalogue.data.source.response.ItemTvShow

interface CatalogueDataSource {

    fun getMovie(): LiveData<List<ItemMovie>>

    fun getMovieById(movieId: Int): LiveData<ItemMovie>

    fun getTvShow(): LiveData<List<ItemTvShow>>

    fun getTvShowById(tvShowId: Int): LiveData<ItemTvShow>
}