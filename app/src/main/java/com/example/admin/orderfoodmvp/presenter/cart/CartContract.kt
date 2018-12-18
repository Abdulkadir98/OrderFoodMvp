package com.example.admin.orderfoodmvp.presenter.cart

import com.example.admin.orderfoodmvp.data.Food

interface CartContract {

    interface View {
        fun showItemsInCart(items: List<Food>)

        fun showEmptyView()

        fun hideEmptyView()

        fun showCouponInput()

        fun showGrandTotal(total: Double)
    }

    interface Presenter {
        fun loadItems()

        fun calculateGrandTotal(items: List<Food>)
    }
}