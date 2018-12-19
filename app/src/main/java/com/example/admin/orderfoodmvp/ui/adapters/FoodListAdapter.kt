package com.example.admin.orderfoodmvp.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.orderfoodmvp.R
import com.example.admin.orderfoodmvp.data.Food
import com.example.admin.orderfoodmvp.util.load
import kotlinx.android.synthetic.main.food_item.view.*

class FoodListAdapter internal constructor(
    context: Context, list: List<Food>,
    val itemClick: (Food) -> Unit,
    val plusBtnClick: (Food, Int) -> Unit,
    val minusBtnClick: (Food, Int) -> Unit
) : RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {


    private var foodItems = ArrayList(list)// Cached copy of food items.

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindFoodItem(food: Food) {
            with(food) {

                val position = adapterPosition
                itemView.foodTitle.text = item_name
                itemView.qtyView.text = quantity.toString()
                itemView.icon.load(image_url)
                itemView.plusBtn.setOnClickListener { plusBtnClick(this, position) }
                itemView.minusBtn.setOnClickListener { minusBtnClick(this, position) }
                itemView.setOnClickListener {
                    itemClick(this)
                }

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListAdapter.FoodViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
        return FoodViewHolder(itemView)
    }

//    internal fun setFoodItems(foodItems: List<Food>) {
//        this.foodItems = foodItems
//        notifyDataSetChanged()
//    }

    internal fun setFoodItem(item: Food, position: Int) {
        this.foodItems.set(position, item)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int  = foodItems.size

    override fun onBindViewHolder(holder: FoodListAdapter.FoodViewHolder, position: Int) {
        holder.bindFoodItem(foodItems[position])
    }
}