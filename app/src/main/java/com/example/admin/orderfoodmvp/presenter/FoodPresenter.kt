package com.example.admin.orderfoodmvp.presenter

import com.example.admin.orderfoodmvp.data.Food
import com.example.admin.orderfoodmvp.data.source.FoodDataSource
import com.example.admin.orderfoodmvp.data.source.FoodRepository

class FoodPresenter(val foodRepository: FoodRepository, val foodView: FoodContract.View): FoodContract.Presenter {

    private var firstLoad = true

    override fun onItemClicked(food: Food) {
        foodView.showDescription(food)
    }


    override fun loadFoodItems() {
//        foodView.setLoadingIndicator(true)


        foodRepository.getFoodItems(object : FoodDataSource.LoadFoodItemsCallback{
            override fun onFoodItemsLoaded(items: List<Food>) {

                foodView.setLoadingIndicator(false)
                foodView.showFoodItems(items)

                if (firstLoad) {
                    foodRepository.saveFoodItems(items)
                    firstLoad = false
                }


            }

            override fun onDataNotAvailable(errorMessage: String) {
            }

        })
    }
}