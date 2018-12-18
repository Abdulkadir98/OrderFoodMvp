package com.example.admin.orderfoodmvp.data.source.remote

import android.util.Log
import com.example.admin.orderfoodmvp.data.Food
import com.example.admin.orderfoodmvp.data.source.FoodDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object FoodRemoteDataSource : FoodDataSource {
    override fun getFoodItemsInCart(callback: FoodDataSource.LoadFoodItemsCallback) {

    }

    override fun incrementQuantity(name: String) {

    }

    override fun decrementQuantity(name: String) {
    }

    override fun refreshFoodItems() {

    }

    override fun saveFoodItems(items: List<Food>) {

    }

    override fun getFoodItems(callback: FoodDataSource.LoadFoodItemsCallback) {
        val apiServices:APIServices = RetrofitClient
            .getClient("https://android-full-time-task.firebaseio.com")
            .create(APIServices::class.java)

        val call: Call<List<Food>> = apiServices.loadFoodItems()
        call.enqueue(object : Callback<List<Food>> {
            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                Log.d("in", "onResponse: " + t.getLocalizedMessage());
                callback.onDataNotAvailable("Error in fetching data")

            }

            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {

                if(response.code() == 200){
                    Log.d("MainActivity", "data: ${response.body()}")
                    callback.onFoodItemsLoaded(response.body()!!)
                }


            }

        })
    }
}