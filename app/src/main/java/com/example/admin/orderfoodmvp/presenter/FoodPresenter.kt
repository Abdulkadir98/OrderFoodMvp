package com.example.admin.orderfoodmvp.presenter

import android.util.Log
import com.example.admin.orderfoodmvp.data.Food
import com.example.admin.orderfoodmvp.data.source.FoodDataSource
import com.example.admin.orderfoodmvp.data.source.FoodRepository

class FoodPresenter(val foodRepository: FoodRepository, val foodView: FoodContract.View): FoodContract.Presenter {
    override fun itemsInCart() {
        foodRepository.getFoodItemsInCart(object: FoodDataSource.LoadFoodItemsCallback{
            override fun onFoodItemsLoaded(items: List<Food>) {

            }

            override fun onDataNotAvailable(errorMessage: String) {
            }

        })
    }

    override fun filterBasedOnRating(items: List<Food>) {
        val sortedList = items.sortedWith(compareBy { it.average_rating })
        foodView.showFilteredFoodItems(sortedList)
    }

    override fun filterBasedOnPrice(items: List<Food>) {
        val sortedList = items.sortedWith(compareBy { it.item_price })
        foodView.showFilteredFoodItems(sortedList)
    }

    override fun onFilterClicked() {
        foodView.showFilterDialog()
    }

    override fun onPlusBtnClicked(itemName: String) {
        foodRepository.incrementQuantity(itemName)
        foodView.updateQuantity("Added to cart!")

    }

    override fun onMinusBtnClicked(itemName: String) {
        foodRepository.decrementQuantity(itemName)
        foodView.updateQuantity("Removed from cart!")
    }


    override fun onItemClicked(food: Food) {
        foodView.showDescription(food)
    }


    override fun loadFoodItems() {
//        foodView.setLoadingIndicator(true)


        foodRepository.getFoodItems(object : FoodDataSource.LoadFoodItemsCallback{
            override fun onFoodItemsLoaded(items: List<Food>) {

                foodView.setLoadingIndicator(false)
                foodView.showFoodItems(items)

                val itemCount = items.filter { it.quantity > 0 }.size
                Log.d("FoodPresenter:Items ", "${itemCount}")
                foodView.updateCartMenuItemWithItemsInCart(itemCount)

                    //foodRepository.saveFoodItems(items)



            }

            override fun onDataNotAvailable(errorMessage: String) {
            }

        })
    }
}