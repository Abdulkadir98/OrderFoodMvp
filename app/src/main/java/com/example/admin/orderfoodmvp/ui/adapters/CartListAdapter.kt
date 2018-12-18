package com.example.admin.orderfoodmvp.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.orderfoodmvp.R
import com.example.admin.orderfoodmvp.data.Food
import com.example.admin.orderfoodmvp.util.load
import kotlinx.android.synthetic.main.cart_item.view.*

class CartListAdapter(context: Context, list: List<Food>) : RecyclerView.Adapter<CartViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var items = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = inflater.inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(itemView)
    }

    override fun getItemCount(): Int  = items.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    internal fun setItems(items: List<Food>) {
        this.items = items
        notifyDataSetChanged()
    }

}

class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindItem(food: Food) {
        with(food) {
            itemView.foodTitle.text = item_name
            itemView.icon.load(image_url)
            itemView.price.text = "Price (1 qty): ${item_price}"
            val totalPrice = item_price * quantity
            itemView.totalPrice.text = "Total Price (1x${quantity}): ${totalPrice}"

        }
    }
}