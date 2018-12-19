package com.example.admin.orderfoodmvp.presenter

import com.example.admin.orderfoodmvp.data.Food

interface FoodContract {
    interface View {

        fun showFoodItems(items: List<Food>)

        fun setLoadingIndicator(active: Boolean)

        fun showNoFoodItems()

        fun showDescription(food: Food)

        fun updateQuantity(message: String, item: Food, position: Int)

        fun showFilterDialog()

        fun showFilteredFoodItems(items: List<Food>)

        fun updateCartMenuItemWithItemsInCart(itemCount: Int)

    }

    interface Presenter {

        fun loadFoodItems()

        fun onItemClicked(food: Food)

        fun onFilterClicked()

        fun onPlusBtnClicked(item: Food, position: Int)

        fun onMinusBtnClicked(item: Food, position: Int)

        fun filterBasedOnPrice(items: List<Food>)

        fun filterBasedOnRating(items: List<Food>)

        fun itemsInCart()
    }
}