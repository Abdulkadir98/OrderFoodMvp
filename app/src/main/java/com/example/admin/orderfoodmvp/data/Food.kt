package com.example.admin.orderfoodmvp.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "food_table")
data class Food(val average_rating: Double,
                val image_url: String,
                @PrimaryKey val item_name: String,
                val item_price: Double,
                var quantity: Int)
