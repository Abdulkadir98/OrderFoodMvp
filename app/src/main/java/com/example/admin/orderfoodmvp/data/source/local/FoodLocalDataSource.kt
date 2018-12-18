package com.example.admin.orderfoodmvp.data.source.local

import android.app.Application
import com.example.admin.orderfoodmvp.data.Food
import com.example.admin.orderfoodmvp.data.source.FoodDataSource
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FoodLocalDataSource(var foodDao: FoodDao? = null, application: Application) : FoodDataSource {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    init {
        foodDao = FoodDatabase.getInstance(application, scope).foodDao()

    }

    override fun getFoodItemsInCart(callback: FoodDataSource.LoadFoodItemsCallback) {
        scope.launch(Dispatchers.IO) {
            val items = foodDao?.getFoodItemsInCart()
            withContext(Dispatchers.IO) {
                callback.onFoodItemsLoaded(items!!)
            }
        }
    }

    override fun incrementQuantity(name: String) {
        scope.launch(Dispatchers.IO) {
            foodDao?.increaseQuantityOfFood(name)
        }
    }

    override fun decrementQuantity(name: String) {
        scope.launch(Dispatchers.IO) {
            foodDao?.decreaseQuantityOfFood(name)
        }
    }

    override fun refreshFoodItems() {

    }


    override fun saveFoodItems(items: List<Food>) {
        scope.launch(Dispatchers.IO) {
            foodDao?.insert(items)
        }
    }


    override fun getFoodItems(callback: FoodDataSource.LoadFoodItemsCallback) {
         scope.launch(Dispatchers.IO) {

             val items = foodDao?.getFoodItems()!!
             withContext(Dispatchers.Main) {
                 callback.onFoodItemsLoaded(items)
             }
        }
    }






}