package com.example.admin.orderfoodmvp.data.source

import com.example.admin.orderfoodmvp.data.Food

interface FoodDataSource {

    interface LoadFoodItemsCallback {
        fun onFoodItemsLoaded(items: List<Food>)

        fun onDataNotAvailable(errorMessage: String)
    }

    fun getFoodItems(callback: LoadFoodItemsCallback)

    fun saveFoodItems(items: List<Food>)

    fun refreshFoodItems()
}