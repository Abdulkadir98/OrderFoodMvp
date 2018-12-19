package com.example.admin.orderfoodmvp.presenter.cart

import com.example.admin.orderfoodmvp.data.Food
import com.example.admin.orderfoodmvp.data.source.FoodDataSource
import com.example.admin.orderfoodmvp.data.source.FoodRepository

class CartPresenter(val repository: FoodRepository, val cartView: CartContract.View): CartContract.Presenter {
    override fun calculateGrandTotal(items: List<Food>) {

        val grandTotal = items.map { it.item_price }.reduce { acc, price -> price + acc  }
        cartView.showGrandTotal(grandTotal)
    }

    override fun loadItems() {
        repository.getFoodItemsInCart(object: FoodDataSource.LoadFoodItemsCallback{
            override fun onFoodItemsLoaded(items: List<Food>) {
                cartView.hideEmptyView()
                cartView.showCouponInput()
                cartView.showItemsInCart(items)
                calculateGrandTotal(items)
            }

            override fun onDataNotAvailable(errorMessage: String) {
            }

        })
    }
}