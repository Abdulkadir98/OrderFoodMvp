package com.example.admin.orderfoodmvp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import java.util.*

class MainActivity : AppCompatActivity(), FoodContract.View {



    private lateinit var presenter: FoodPresenter
    private lateinit var recyclerView: RecyclerView


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

        recyclerView = find(R.id.recyclerView)


        // primary sections of the activity.
        presenter = FoodPresenter(FoodRepository(FoodRemoteDataSource, FoodLocalDataSource(application = this.application))
                                                                        , this)
        presenter.loadFoodItems()

        val adapter = FoodListAdapter(this, ArrayList<Food>(0), presenter::onItemClicked,
            presenter::onPlusBtnClicked, presenter::onMinusBtnClicked)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)





    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "OnResume:called")
    }

    override fun updateQuantity(message: String) {
        toast(message)
    }

    override fun showFoodItems(items: List<Food>) {

        recyclerView.apply {
            adapter = FoodListAdapter(applicationContext, items, presenter::onItemClicked,
                presenter::onPlusBtnClicked, presenter::onMinusBtnClicked)
        }
        Log.d("MainActivity", "data: ${items}")
    }
    override fun setLoadingIndicator(active: Boolean) {
        if(active)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
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
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_cart) {
            startActivity<CartActivity>()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}



