package com.example.admin.orderfoodmvp.presenter

import com.example.admin.orderfoodmvp.data.Food

interface FoodContract {
    interface View {

        fun showFoodItems(items: List<Food>)

        fun setLoadingIndicator(active: Boolean)

        fun showNoFoodItems()

        fun showDescription(food: Food)

        fun updateQuantity(message: String)

        fun showFilterDialog()

        fun showFilteredFoodItems(items: List<Food>)

    }

    interface Presenter {

        fun loadFoodItems()

        fun onItemClicked(food: Food)

        fun onFilterClicked()

        fun onPlusBtnClicked(itemName: String)

        fun onMinusBtnClicked(itemName: String)

        fun filterBasedOnPrice(items: List<Food>)

        fun filterBasedOnRating(items: List<Food>)
    }
}