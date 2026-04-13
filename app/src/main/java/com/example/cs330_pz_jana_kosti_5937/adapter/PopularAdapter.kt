package com.example.cs330_pz_jana_kosti_5937.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cs330_pz_jana_kosti_5937.databinding.ViewholderPopularBinding
import com.example.cs330_pz_jana_kosti_5937.domain.PopularModel

class PopularAdapter(
    private val items: MutableList<PopularModel>
) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    class ViewHolder(val binding: ViewholderPopularBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderPopularBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.titlePopular.text = item.title
        holder.binding.extraPopular.text = item.extra
        holder.binding.pricePopular.text = "$" + String.format("%.2f", item.price)

        Glide.with(holder.itemView.context)
            .load(item.picUrl.firstOrNull())
            .into(holder.binding.picPopular)
    }

    override fun getItemCount(): Int = items.size
}
