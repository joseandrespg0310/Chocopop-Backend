package com.example.chocopop


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chocopop.databinding.ItemProductBinding

open class ProductAdapter : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.product = product

            // Cargar la imagen usando Glide
            Glide.with(binding.productImage.context)
                .load(product.imagen)
                .into(binding.productImage)

            // Configurar botones de cantidad
            binding.decreaseButton.setOnClickListener {
                if (product.cantidad > 1) {
                    product.cantidad--
                    notifyItemChanged(adapterPosition)
                }
            }

            binding.increaseButton.setOnClickListener {
                product.cantidad++
                notifyItemChanged(adapterPosition)
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem._id == newItem._id
        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }
}
