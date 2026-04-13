package com.example.cs330_pz_jana_kosti_5937.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cs330_pz_jana_kosti_5937.adapter.CategoryAdapter
import com.example.cs330_pz_jana_kosti_5937.adapter.PopularAdapter
import com.example.cs330_pz_jana_kosti_5937.databinding.ActivityMainBinding
import com.example.cs330_pz_jana_kosti_5937.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cartNavButton.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        initCategory()
        initBanner()
        initPopular()
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE
        binding.categoryView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewModel.loadCategory().observe(this) { categories ->
            binding.categoryView.adapter = CategoryAdapter(categories) { category ->
                if (category.title.equals("Cappuccino", ignoreCase = true)) {
                    startActivity(Intent(this, CappuccinoActivity::class.java))
                }
            }
            binding.progressBarCategory.visibility = View.GONE
        }
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE

        viewModel.loadBanner().observe(this) { banners ->
            val bannerItem = banners.firstOrNull()
            if (bannerItem != null) {
                Glide.with(this)
                    .load(bannerItem.url)
                    .into(binding.banner)
            }
            binding.progressBarBanner.visibility = View.GONE
        }
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        binding.popularView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewModel.loadPopular().observe(this) {
            binding.popularView.adapter = PopularAdapter(it)
            binding.progressBarPopular.visibility = View.GONE
        }
    }
}
