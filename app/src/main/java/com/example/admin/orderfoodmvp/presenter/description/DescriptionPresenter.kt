package com.example.admin.orderfoodmvp.presenter.description

import com.example.admin.orderfoodmvp.data.source.FoodRepository

class DescriptionPresenter(val foodRepository: FoodRepository, val descriptionView: DescriptionContract.View) :
                                                                DescriptionContract.Presenter {
    override fun onAddBtnClicked(itemName: String) {
        foodRepository.incrementQuantity(itemName)
        descriptionView.updateQuantity("Added to cart!")
    }

    override fun onRemoveBtnClicked(itemName: String) {
        foodRepository.decrementQuantity(itemName)
        descriptionView.updateQuantity("Removed from cart!")
    }
}