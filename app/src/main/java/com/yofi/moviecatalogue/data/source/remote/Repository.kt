package com.yofi.moviecatalogue.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yofi.moviecatalogue.data.source.response.ItemMovie
import com.yofi.moviecatalogue.data.source.response.ItemTvShow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class Repository private constructor(private val RemoteDataSource: RemoteDataSource) :
    CatalogueDataSource {

    private val _isLoading = MutableLiveData<Boolean>()

    companion object {
        @Volatile
        private var instance: Repository? = null


        fun getInstance(catalogueRemoteData: RemoteDataSource): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(catalogueRemoteData).apply { instance = this }
            }

    }

    override fun getMovie(): LiveData<List<ItemMovie>> {
        val listMoviesOutput = MutableLiveData<List<ItemMovie>>()
        _isLoading.value = true
        CoroutineScope(IO).launch {
            RemoteDataSource.getMovies(object :
                RemoteDataSource.LoadMovieCallback {
                override fun onAllMovieReceived(movieCatalogueResponse: List<ItemMovie>) {
                    val listMovies = ArrayList<ItemMovie>()
                    for (response in movieCatalogueResponse) {
                        val movie = ItemMovie(
                            response.id,
                            response.title,
                            response.posterPath,
                            response.releaseDate,
                            response.voteAverage,
                            response.overview
                        )
                        listMovies.add(movie)
                    }
                    listMoviesOutput.postValue(listMovies)
                    _isLoading.postValue(false)
                }
            })
        }
        return listMoviesOutput
    }

    override fun getTvShow(): LiveData<List<ItemTvShow>> {
        val listTvShowOutput = MutableLiveData<List<ItemTvShow>>()
        CoroutineScope(IO).launch {
            RemoteDataSource.getTvShows(object :
                RemoteDataSource.LoadTvShowCallback {
                override fun onAllTvShowReceived(tvShowCatalogueResponse: List<ItemTvShow>) {
                    val listTvShow = ArrayList<ItemTvShow>()
                    for (response in tvShowCatalogueResponse) {
                        val tvShows = ItemTvShow(
                            response.id,
                            response.originalName,
                            response.posterPath,
                            response.firstAirDate,
                            response.voteAverage,
                            response.overview
                        )
                        listTvShow.add(tvShows)
                        _isLoading.postValue(false)
                    }
                    listTvShowOutput.postValue(listTvShow)
                }
            })
        }
        return listTvShowOutput
    }

    override fun getTvShowById(tvShowId: Int): LiveData<ItemTvShow> {
        val tvShowDetailOutput = MutableLiveData<ItemTvShow>()
        CoroutineScope(IO).launch {
            RemoteDataSource.getTvShowDetail(
                tvShowId,
                object : RemoteDataSource.LoadTvShowsByIdCallback {
                    override fun onTvShowsDetailReceived(tvShowsCatalogue: ItemTvShow) {
                        val tvShowsDetail = ItemTvShow(
                            tvShowsCatalogue.id,
                            tvShowsCatalogue.originalName,
                            tvShowsCatalogue.posterPath,
                            tvShowsCatalogue.firstAirDate,
                            tvShowsCatalogue.voteAverage,
                            tvShowsCatalogue.overview
                        )
                        tvShowDetailOutput.postValue(tvShowsDetail)
                        _isLoading.postValue(false)
                    }
                })
        }
        return tvShowDetailOutput
    }

    override fun getMovieById(movieId: Int): LiveData<ItemMovie> {
        val movieDetailOutput = MutableLiveData<ItemMovie>()
        CoroutineScope(IO).launch {
            RemoteDataSource.getMovieDetail(
                movieId,
                object : RemoteDataSource.LoadMovieByIdCallback {
                    override fun onMovieDetailReceived(movieCatalogueResponse: ItemMovie) {
                        val moviesDetail = ItemMovie(
                            movieCatalogueResponse.id,
                            movieCatalogueResponse.title,
                            movieCatalogueResponse.posterPath,
                            movieCatalogueResponse.releaseDate,
                            movieCatalogueResponse.voteAverage,
                            movieCatalogueResponse.overview
                        )
                        movieDetailOutput.postValue(moviesDetail)
                        _isLoading.postValue(false)
                    }
                })
        }
        return movieDetailOutput
    }
}
