package com.example.admin.orderfoodmvp.presenter.description

interface DescriptionContract {
    interface View {
        fun updateQuantity(message: String)
    }

    interface Presenter {
        fun onAddBtnClicked(itemName: String)

        fun onRemoveBtnClicked(itemName: String)

        fun itemsInCart()
    }
}