package com.example.admin.orderfoodmvp.data.source.local

import android.app.Application
import com.example.admin.orderfoodmvp.data.Food
import com.example.admin.orderfoodmvp.data.source.FoodDataSource
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FoodLocalDataSource(var foodDao: FoodDao? = null, application: Application) : FoodDataSource {
    override fun refreshFoodItems() {

    }

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    init {
        foodDao = FoodDatabase.getInstance(application, scope).foodDao()

    }
    override fun saveFoodItems(items: List<Food>) {
        scope.launch(Dispatchers.IO) {
            foodDao?.insert(items)
        }
    }


    override fun getFoodItems(callback: FoodDataSource.LoadFoodItemsCallback) {
        var items: List<Food> = emptyList()
        scope.launch(Dispatchers.IO) {
            items = async { loadItems() }.await()
        }
        if(items.isEmpty())
            callback.onDataNotAvailable("Not available")
        callback.onFoodItemsLoaded(items)
    }

    private suspend fun loadItems() : List<Food>  = foodDao?.getFoodItems()!!




}