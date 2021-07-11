package io.aethibo.rollback.features.products.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import io.aethibo.rollback.R
import io.aethibo.rollback.domain.mapped.ProductItem
import java.math.BigDecimal
import java.math.RoundingMode

class ProductsAdapter : ListAdapter<ProductItem, ProductsAdapter.ProductsViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<ProductItem>() {
        override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.onBind(getItem(position) ?: return)
    }

    inner class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(product: ProductItem) = with(itemView) {

            /**
             * Get views
             */
            val title = findViewById<TextView>(R.id.tvItemProductTitle)
            val price = findViewById<TextView>(R.id.tvItemProductPrice)
            val biggerPrice = findViewById<TextView>(R.id.tvItemProductBiggerPrice)
            val category = findViewById<TextView>(R.id.cpItemProductCategory)
            val image = findViewById<ImageView>(R.id.ivItemProductImage)

            /**
             * Init values
             */
            title.text = product.title
            category.text = product.category

            price.apply {
                text = context.getString(R.string.labelProductPrice, product.price.toString())
                setTextColor(ContextCompat.getColor(itemView.context, R.color.teal_700))
            }

            val newPrice = product.price.times(1.5)
            val rounded = BigDecimal(newPrice).setScale(2, RoundingMode.HALF_UP).toDouble()
            biggerPrice.apply {
                text = context.getString(R.string.labelProductPrice, rounded.toString())
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }

            image.load(product.image) {
                crossfade(true)
                transformations(RoundedCornersTransformation(9f))
            }

            setOnClickListener {
                onProductClickListener?.let { click -> click(product) }
            }
        }
    }

    /**
     * Click listeners
     */
    private var onProductClickListener: ((ProductItem) -> Unit)? = null

    fun onProductEventClickListener(listener: (ProductItem) -> Unit) {
        onProductClickListener = listener
    }
}