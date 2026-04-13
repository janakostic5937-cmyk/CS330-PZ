package com.example.cs330_pz_jana_kosti_5937.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.cs330_pz_jana_kosti_5937.databinding.ActivityPumpkinLatteBinding

class PumpkinLatteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPumpkinLatteBinding
    private var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPumpkinLatteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backButton.setOnClickListener { finish() }

        binding.titlePage.text = intent.getStringExtra(EXTRA_TITLE) ?: "Pumpkin Latte"
        binding.descriptionValue.text = intent.getStringExtra(EXTRA_DESCRIPTION).orEmpty()
        binding.ratingValue.text =
            String.format("%.1f", intent.getDoubleExtra(EXTRA_RATING, 0.0))
        binding.priceValue.text =
            "$" + String.format("%.2f", intent.getDoubleExtra(EXTRA_PRICE, 0.0))

        Glide.with(this)
            .load(intent.getStringExtra(EXTRA_IMAGE_URL))
            .into(binding.heroImage)

        setupSizeButtons()
        setupQuantityControls()
        setupAddToCartButton()
    }

    private fun setupSizeButtons() {
        val sizeViews = listOf(binding.sizeSmall, binding.sizeMedium, binding.sizeLarge)

        fun selectSize(selectedView: android.widget.TextView) {
            sizeViews.forEach { view ->
                val isSelected = view == selectedView
                view.setBackgroundResource(
                    if (isSelected) com.example.cs330_pz_jana_kosti_5937.R.drawable.brown_full_corner
                    else com.example.cs330_pz_jana_kosti_5937.R.drawable.cream_full_corner
                )
                view.setTextColor(
                    getColor(
                        if (isSelected) com.example.cs330_pz_jana_kosti_5937.R.color.cream
                        else com.example.cs330_pz_jana_kosti_5937.R.color.darkBrown
                    )
                )
            }
        }

        sizeViews.forEach { view ->
            view.setOnClickListener { selectSize(view) }
        }
    }

    private fun setupQuantityControls() {
        binding.quantityValue.text = quantity.toString()

        binding.plusButton.setOnClickListener {
            quantity += 1
            binding.quantityValue.text = quantity.toString()
        }

        binding.minusButton.setOnClickListener {
            if (quantity > 1) {
                quantity -= 1
                binding.quantityValue.text = quantity.toString()
            }
        }
    }

    private fun setupAddToCartButton() {
        binding.addToCartButton.setOnClickListener {
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_IMAGE_URL = "extra_image_url"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_RATING = "extra_rating"
        const val EXTRA_PRICE = "extra_price"
    }
}
