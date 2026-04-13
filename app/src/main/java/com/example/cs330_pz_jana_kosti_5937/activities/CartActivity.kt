package com.example.cs330_pz_jana_kosti_5937.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cs330_pz_jana_kosti_5937.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private val deliveryFee = 15.0
    private val totalTax = 0.87
    private val itemTotals = mutableMapOf<String, Double>()

    private data class CartItemViews(
        val key: String,
        val priceView: TextView,
        val quantityView: TextView,
        val minusButton: TextView,
        val plusButton: TextView,
        val unitPrice: Double
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backButton.setOnClickListener { finish() }

        setupCartItem(
            CartItemViews(
                key = "cappoccino",
                priceView = binding.cappoccinoPrice,
                quantityView = binding.cappoccinoQuantityValue,
                minusButton = binding.cappoccinoMinusButton,
                plusButton = binding.cappoccinoPlusButton,
                unitPrice = 4.50
            )
        )
        setupCartItem(
            CartItemViews(
                key = "americano",
                priceView = binding.americanoPrice,
                quantityView = binding.americanoQuantityValue,
                minusButton = binding.americanoMinusButton,
                plusButton = binding.americanoPlusButton,
                unitPrice = 3.80
            )
        )
        setupCartItem(
            CartItemViews(
                key = "pumpkinLatte",
                priceView = binding.pumpkinLattePrice,
                quantityView = binding.pumpkinLatteQuantityValue,
                minusButton = binding.pumpkinLatteMinusButton,
                plusButton = binding.pumpkinLattePlusButton,
                unitPrice = 5.99
            )
        )
        setupCartItem(
            CartItemViews(
                key = "cortado",
                priceView = binding.cortadoPrice,
                quantityView = binding.cortadoQuantityValue,
                minusButton = binding.cortadoMinusButton,
                plusButton = binding.cortadoPlusButton,
                unitPrice = 4.20
            )
        )

        binding.deliveryValue.text = formatPrice(deliveryFee)
        binding.taxValue.text = formatPrice(totalTax)
        updateSummary()
    }

    private fun setupCartItem(item: CartItemViews) {
        var quantity = 1

        fun render() {
            item.quantityView.text = quantity.toString()
            val total = item.unitPrice * quantity
            item.priceView.text = formatPrice(total)
            itemTotals[item.key] = total
            updateSummary()
        }

        item.plusButton.setOnClickListener {
            quantity += 1
            render()
        }

        item.minusButton.setOnClickListener {
            if (quantity > 1) {
                quantity -= 1
                render()
            }
        }

        render()
    }

    private fun updateSummary() {
        val subtotal = itemTotals.values.sum()
        val total = subtotal + deliveryFee + totalTax
        binding.subtotalValue.text = formatPrice(subtotal)
        binding.totalValue.text = formatPrice(total)
    }

    private fun formatPrice(value: Double): String {
        return "$" + String.format("%.2f", value)
    }
}
