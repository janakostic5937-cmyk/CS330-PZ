package com.example.cs330_pz_jana_kosti_5937.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cs330_pz_jana_kosti_5937.domain.BannerModel
import com.example.cs330_pz_jana_kosti_5937.domain.CategoryModel
import com.example.cs330_pz_jana_kosti_5937.domain.PopularModel
import com.example.cs330_pz_jana_kosti_5937.repository.MainRepository

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        return repository.loadCategory()
    }

    fun loadBanner(): LiveData<MutableList<BannerModel>>{
        return repository.loadBanner()

    }

    fun loadPopular(): LiveData<MutableList<PopularModel>> {
        return repository.loadPopular()
    }

    fun loadCappuccinoItems(): LiveData<MutableList<PopularModel>> {
        return repository.loadCappuccinoItems()
    }
}
