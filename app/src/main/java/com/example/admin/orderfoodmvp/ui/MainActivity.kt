package com.example.admin.orderfoodmvp.ui

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.admin.orderfoodmvp.R
import com.example.admin.orderfoodmvp.data.Food
import com.example.admin.orderfoodmvp.data.source.FoodRepository
import com.example.admin.orderfoodmvp.data.source.local.FoodLocalDataSource
import com.example.admin.orderfoodmvp.data.source.remote.FoodRemoteDataSource
import com.example.admin.orderfoodmvp.presenter.FoodContract
import com.example.admin.orderfoodmvp.presenter.FoodPresenter
import com.example.admin.orderfoodmvp.ui.adapters.FoodListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), FoodContract.View {



    private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: FoodPresenter
    private lateinit var cartMenuItem: MenuItem
    private lateinit var cartItemCountView: TextView
    private var items = emptyList<Food>()

    private var adapter = FoodListAdapter(this, ArrayList(0), {},
        {item: Food, postion: Int ->}, {item: Food, position: Int->}
    )


    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "OnCreate:called")

        setSupportActionBar(toolbar)
        toolbar.title = "Order food"

          presenter = FoodPresenter(
            FoodRepository(FoodRemoteDataSource, FoodLocalDataSource(application = this.application))
            , this)

        recyclerView = find(R.id.recyclerView)


        // primary sections of the activity.

        presenter.loadFoodItems()


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)





    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "OnResume:called")
    }
    override fun updateCartMenuItemWithItemsInCart(itemCount: Int) {
        mCartItemCount = itemCount
        Log.d("MainActivity", "cart items: ${mCartItemCount}")

        if(::cartItemCountView.isInitialized)
            setUpBadge()

    }

    override fun showFilterDialog() {
        val choiceItems = resources.getStringArray(R.array.dialog_multi_choice_array)
        val checkedItems = arrayListOf(false, false).toBooleanArray()

        AlertDialog.Builder(this)
            .setTitle("Filter based on")
            .setMultiChoiceItems(choiceItems, checkedItems, object: DialogInterface.OnMultiChoiceClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
                    if(checkedItems[which] && which == 0){
                        presenter.filterBasedOnPrice(items)
                    }
                    if(checkedItems[which] && which == 1){
                        presenter.filterBasedOnRating(items)
                    }
                }
            })
            .setPositiveButton("Ok", null)
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun updateQuantity(message: String,  item: Food, position: Int) {
        toast(message)
        adapter.setFoodItem(item, position)

        //necessary to update the menu icon with the latest item added to cart
        presenter.itemsInCart()
    }

    override fun showFoodItems(items: List<Food>) {

        this.items = items
        adapter = FoodListAdapter(applicationContext, items, presenter::onItemClicked,
                presenter::onPlusBtnClicked, presenter::onMinusBtnClicked)
        recyclerView.adapter = adapter
        Log.d("MainActivity", "data: ${items}")
    }
    override fun setLoadingIndicator(active: Boolean) {
        if(active)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

    override fun showFilteredFoodItems(items: List<Food>) {
        Log.d("MainActivity", "filtered items: ${items}")
        recyclerView.apply {
            adapter = FoodListAdapter(applicationContext,items, presenter::onItemClicked,
                presenter::onPlusBtnClicked, presenter::onMinusBtnClicked)
        }
    }
    override fun showNoFoodItems() {

    }

    override fun showDescription(food: Food) {
        startActivity<DescriptionActivity>(DescriptionActivity.TITLE to food.item_name,
            DescriptionActivity.RATING to food.average_rating,
            DescriptionActivity.IMAGE_URL to food.image_url,
            DescriptionActivity.PRICE to food.item_price)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        cartMenuItem = menu?.findItem(R.id.action_cart)!!

        val actionView = MenuItemCompat.getActionView(cartMenuItem)
        cartItemCountView = actionView.findViewById(R.id.cart_badge)

        setUpBadge()

        actionView.setOnClickListener { onOptionsItemSelected(cartMenuItem) }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

//        if (id == R.id.action_cart) {
//            startActivity<CartActivity>()
//            return true
//        }
        when(id) {
            R.id.action_cart -> {startActivity<CartActivity>()
                true}
            R.id.action_filter -> {
                presenter.onFilterClicked()
                true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private var mCartItemCount: Int = 0

    private fun setUpBadge() {
        if (cartItemCountView != null) {
            if (mCartItemCount == 0) {
                if (cartItemCountView.getVisibility() != View.GONE) {
                    cartItemCountView.setVisibility(View.GONE);
                }
            } else {
                cartItemCountView.setText((Math.min(mCartItemCount, 99)).toString());
                if (cartItemCountView.getVisibility() != View.VISIBLE) {
                    cartItemCountView.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}




