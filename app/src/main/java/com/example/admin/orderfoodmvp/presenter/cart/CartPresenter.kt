package com.example.admin.orderfoodmvp.presenter.cart

import com.example.admin.orderfoodmvp.data.Food
import com.example.admin.orderfoodmvp.data.source.FoodDataSource
import com.example.admin.orderfoodmvp.data.source.FoodRepository

class CartPresenter(val repository: FoodRepository, val cartView: CartContract.View): CartContract.Presenter {
    override fun calculateGrandTotal(items: List<Food>) {
        var grandTotal = 0.0
        var total: Double = 0.0
        items.forEach {
            total = it.item_price * it.quantity
            grandTotal += total

        }
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