package com.example.cs330_pz_jana_kosti_5937.domain

data class PopularModel(
    var title: String = "",
    var extra: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var rating: Double = 0.0,
    var picUrl: MutableList<String> = mutableListOf()
)
