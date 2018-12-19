package com.example.admin.orderfoodmvp.presenter.description

import com.example.admin.orderfoodmvp.data.Food
import com.example.admin.orderfoodmvp.data.source.FoodDataSource
import com.example.admin.orderfoodmvp.data.source.FoodRepository

class DescriptionPresenter(val foodRepository: FoodRepository, val descriptionView: DescriptionContract.View) :
                                                                DescriptionContract.Presenter {
    override fun itemsInCart() {
        foodRepository.getFoodItemsInCart(object: FoodDataSource.LoadFoodItemsCallback{
            override fun onFoodItemsLoaded(items: List<Food>) {

            }

            override fun onDataNotAvailable(errorMessage: String) {
            }

        })
    }

    override fun onAddBtnClicked(itemName: String) {
        foodRepository.incrementQuantity(itemName)
        descriptionView.updateQuantity("Added to cart!")
    }

    override fun onRemoveBtnClicked(itemName: String) {
        foodRepository.decrementQuantity(itemName)
        descriptionView.updateQuantity("Removed from cart!")
    }
}