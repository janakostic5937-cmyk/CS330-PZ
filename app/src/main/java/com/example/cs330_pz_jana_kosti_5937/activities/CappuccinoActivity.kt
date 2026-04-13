package com.example.cs330_pz_jana_kosti_5937.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cs330_pz_jana_kosti_5937.adapter.CappuccinoAdapter
import com.example.cs330_pz_jana_kosti_5937.databinding.ActivityCappuccinoBinding
import com.example.cs330_pz_jana_kosti_5937.viewmodel.MainViewModel

class CappuccinoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCappuccinoBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCappuccinoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backButton.setOnClickListener { finish() }
        initItems()
    }

    private fun initItems() {
        binding.progressBarItems.visibility = android.view.View.VISIBLE
        binding.cappuccinoItemsView.layoutManager = GridLayoutManager(this, 2)

        viewModel.loadCappuccinoItems().observe(this) { items ->
            binding.cappuccinoItemsView.adapter = CappuccinoAdapter(items) { item ->
                if (item.title.equals("Pumpkin Latte", ignoreCase = true)) {
                    startActivity(
                        Intent(this, PumpkinLatteActivity::class.java).apply {
                            putExtra(PumpkinLatteActivity.EXTRA_TITLE, item.title)
                            putExtra(PumpkinLatteActivity.EXTRA_IMAGE_URL, item.picUrl.firstOrNull())
                            putExtra(PumpkinLatteActivity.EXTRA_DESCRIPTION, item.description)
                            putExtra(PumpkinLatteActivity.EXTRA_RATING, item.rating)
                            putExtra(PumpkinLatteActivity.EXTRA_PRICE, item.price)
                        }
                    )
                }
            }
            binding.progressBarItems.visibility = android.view.View.GONE
        }
    }
}
