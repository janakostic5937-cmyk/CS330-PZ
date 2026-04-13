package com.example.cs330_pz_jana_kosti_5937.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cs330_pz_jana_kosti_5937.R
import com.example.cs330_pz_jana_kosti_5937.databinding.ViewholderCategoryBinding
import com.example.cs330_pz_jana_kosti_5937.domain.CategoryModel

class CategoryAdapter(
    private val items: MutableList<CategoryModel>,
    private val onCategoryClick: (CategoryModel) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    class ViewHolder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.titleCat.text = item.title

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = holder.bindingAdapterPosition

            if (lastSelectedPosition != -1) {
                notifyItemChanged(lastSelectedPosition)
            }
            notifyItemChanged(selectedPosition)
            onCategoryClick(item)
        }

        val isSelected = selectedPosition == position
        holder.binding.titleCat.setBackgroundResource(
            if (isSelected) R.drawable.brown_full_corner else R.drawable.cream_full_corner
        )
        holder.binding.titleCat.setTextColor(
            holder.itemView.context.getColor(
                if (isSelected) R.color.cream else R.color.darkBrown
            )
        )
    }

    override fun getItemCount(): Int = items.size
}
