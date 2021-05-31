package com.yofi.moviecatalogue.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yofi.moviecatalogue.data.DataEntity
import com.yofi.moviecatalogue.data.source.local.Dummy
import com.yofi.moviecatalogue.data.source.remote.Repository
import com.yofi.moviecatalogue.data.source.response.ItemMovie
import com.yofi.moviecatalogue.data.source.response.ItemTvShow

class MainViewModel(private val repository: Repository): ViewModel() {
    fun getListMovie(): LiveData<List<ItemMovie>> =  repository.getMovie()
    fun getListTvShow(): LiveData<List<ItemTvShow>> =  repository.getTvShow()
}