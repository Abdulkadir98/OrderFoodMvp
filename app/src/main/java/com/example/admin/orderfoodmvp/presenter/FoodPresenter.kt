package com.example.admin.orderfoodmvp.presenter

import com.example.admin.orderfoodmvp.data.Food
import com.example.admin.orderfoodmvp.data.source.FoodDataSource
import com.example.admin.orderfoodmvp.data.source.FoodRepository

class FoodPresenter(val foodRepository: FoodRepository, val foodView: FoodContract.View): FoodContract.Presenter {
    override fun itemsInCart() {
        foodRepository.getFoodItemsInCart(object: FoodDataSource.LoadFoodItemsCallback{
            override fun onFoodItemsLoaded(items: List<Food>) {
                foodView.updateCartMenuItemWithItemsInCart(items.size)
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

    override fun onPlusBtnClicked(item: Food, position: Int) {

        foodRepository.incrementQuantity(item.item_name)
        item.apply {
            quantity++
        }
        foodView.updateQuantity("Added to cart!", item, position)

    }

    override fun onMinusBtnClicked(item: Food, position: Int) {

        foodRepository.decrementQuantity(item.item_name)
        item.apply {
            quantity--
        }
        foodView.updateQuantity("Removed from cart!", item, position)
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
                foodView.updateCartMenuItemWithItemsInCart(itemCount)

                    //foodRepository.saveFoodItems(items)



            }

            override fun onDataNotAvailable(errorMessage: String) {

            }

        })
    }
}