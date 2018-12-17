package com.example.admin.orderfoodmvp.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.admin.orderfoodmvp.data.Food

@Dao
interface FoodDao {
    @Insert
    fun insert(items: List<Food>)

    @Query("DELETE FROM food_table")
    fun deleteAll()


    @Query("SELECT * FROM food_table")
    fun getFoodItems() : List<Food>

    @Query("UPDATE food_table SET quantity = quantity + 1 WHERE item_name = :name")
    fun increaseQuantityOfFood(name: String)

    @Query("UPDATE food_table SET quantity = quantity - 1 WHERE quantity > 0 AND item_name = :name")
    fun decreaseQuantityOfFood(name: String)

    @Query("SELECT * FROM food_table WHERE quantity > 0")
    fun getFoodItemsInCart() : List<Food>
}