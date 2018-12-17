package com.example.admin.orderfoodmvp.presenter

import com.example.admin.orderfoodmvp.data.Food

interface FoodContract {
    interface View {

        fun showFoodItems(items: List<Food>)

        fun setLoadingIndicator(active: Boolean)

        fun showNoFoodItems()

        fun showDescription(food: Food)

    }

    interface Presenter {

        fun loadFoodItems()


        fun onItemClicked(food: Food)
    }
}