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
    context: Context, val list: List<Food>,
    val itemClick: (Food) -> Unit
) : RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {



    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var foodItems = list// Cached copy of food items.

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindFoodItem(food: Food) {
            with(food) {

                itemView.foodTitle.text = item_name
                itemView.icon.load(image_url)
                itemView.plusBtn.setOnClickListener {  }
                itemView.minusBtn.setOnClickListener {  }
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

    internal fun setFoodItems(foodItems: List<Food>) {
        this.foodItems = foodItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int  = foodItems.size

    override fun onBindViewHolder(holder: FoodListAdapter.FoodViewHolder, position: Int) {
        holder.bindFoodItem(foodItems[position])
    }
}