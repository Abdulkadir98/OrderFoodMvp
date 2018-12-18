package com.example.admin.orderfoodmvp.data.source

import com.example.admin.orderfoodmvp.data.Food
import java.util.*

class FoodRepository(val foodRemoteDataSource: FoodDataSource,
                     val foodLocalDataSource: FoodDataSource) : FoodDataSource {

    override fun getFoodItemsInCart(callback: FoodDataSource.LoadFoodItemsCallback) {
        foodLocalDataSource.getFoodItemsInCart(object: FoodDataSource.LoadFoodItemsCallback{
            override fun onFoodItemsLoaded(items: List<Food>) {
                callback.onFoodItemsLoaded(items)
            }

            override fun onDataNotAvailable(errorMessage: String) {
                callback.onDataNotAvailable("Not available!")
            }

        })
    }

    override fun incrementQuantity(name: String) {
        foodLocalDataSource.incrementQuantity(name)
    }

    override fun decrementQuantity(name: String) {
        foodLocalDataSource.decrementQuantity(name)
    }

    override fun refreshFoodItems() {
        cacheIsDirty = true
    }

    /**
     * This variable has public visibility so it can be accessed from tests.
     */
    var cachedFoodItems: LinkedHashMap<Int, Food> = LinkedHashMap()

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    var cacheIsDirty = false

    override fun getFoodItems(callback: FoodDataSource.LoadFoodItemsCallback) {

        //getFoodItemsFromRemoteDataSource(callback)

//        if(cachedFoodItems.isNotEmpty() && !cacheIsDirty) {
//            callback.onFoodItemsLoaded(ArrayList(cachedFoodItems.values))
//        }
//
//        else {
            foodLocalDataSource.getFoodItems(object: FoodDataSource.LoadFoodItemsCallback{
                override fun onFoodItemsLoaded(items: List<Food>) {
                    //refreshCache(items)
                    callback.onFoodItemsLoaded(items)
                }

                override fun onDataNotAvailable(errorMessage: String) {
                    getFoodItemsFromRemoteDataSource(callback)
                }

            })
//        }


    }
    private fun refreshCache(items: List<Food>) {
        cachedFoodItems.clear()
        var i = 0;
        items.forEach {
            cachedFoodItems.put(++i, it)
        }
        cacheIsDirty = false
    }

    private fun getFoodItemsFromRemoteDataSource(callback: FoodDataSource.LoadFoodItemsCallback) {
        foodRemoteDataSource.getFoodItems(object: FoodDataSource.LoadFoodItemsCallback {
            override fun onDataNotAvailable(errorMessage: String) {
                callback.onDataNotAvailable(errorMessage)
            }

            override fun onFoodItemsLoaded(items: List<Food>) {
                callback.onFoodItemsLoaded(items)
            }


        })
    }

    override fun saveFoodItems(items: List<Food>) {
        foodLocalDataSource.saveFoodItems(items)
    }

    companion object {
        private var INSTANCE: FoodRepository? = null

        @JvmStatic fun getInstance(foodRemoteDataSource: FoodDataSource, foodLocalDataSource: FoodDataSource): FoodRepository {
            return INSTANCE ?: FoodRepository(foodRemoteDataSource, foodLocalDataSource)
                .apply { INSTANCE = this }
        }
    }

}