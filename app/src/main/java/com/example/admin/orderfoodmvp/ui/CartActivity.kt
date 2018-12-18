package com.example.admin.orderfoodmvp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.example.admin.orderfoodmvp.R
import com.example.admin.orderfoodmvp.data.Food
import com.example.admin.orderfoodmvp.data.source.FoodRepository
import com.example.admin.orderfoodmvp.data.source.local.FoodLocalDataSource
import com.example.admin.orderfoodmvp.data.source.remote.FoodRemoteDataSource
import com.example.admin.orderfoodmvp.presenter.cart.CartContract
import com.example.admin.orderfoodmvp.presenter.cart.CartPresenter
import com.example.admin.orderfoodmvp.ui.adapters.CartListAdapter
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_cart.view.*
import org.jetbrains.anko.find

class CartActivity : AppCompatActivity(), CartContract.View {

    override fun showCouponInput() {
        couponContainer.visibility = View.VISIBLE
    }

    override fun showGrandTotal(total: Double) {
        grandTotalContainer.apply {
            visibility = View.VISIBLE
            grandTotalText.text = "Grand Total: ${total}"
        }
    }

    override fun showEmptyView() {

    }

    override fun hideEmptyView() {
        emptyView.visibility = View.GONE
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var cartPresenter: CartPresenter
    override fun showItemsInCart(items: List<Food>) {


        recyclerView.apply {
            visibility = View.VISIBLE
            adapter = CartListAdapter( applicationContext, items)
        }
        Log.d("CartActivity", "${items}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = find(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = CartListAdapter(this, ArrayList(0))
        recyclerView.adapter = adapter

        cartPresenter = CartPresenter(FoodRepository(FoodRemoteDataSource,
            FoodLocalDataSource(application = this.application)), this)

        cartPresenter.loadItems()
    }
}
