package com.example.admin.orderfoodmvp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import com.example.admin.orderfoodmvp.R
import com.example.admin.orderfoodmvp.util.load
import kotlinx.android.synthetic.main.activity_description.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class DescriptionActivity : AppCompatActivity() {

    private lateinit var plusBtn: AppCompatButton
    private lateinit var minusBtn: AppCompatButton

    companion object {
        val TITLE = "DescriptionActivity:title"
        val RATING = "DescriptionActivity:rating"
        val PRICE = "DescriptionActivity:price"
        val QUANTITY = "DescriptionActivity:quantity"
        val IMAGE_URL = "DescriptionActivity:url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        plusBtn = find(R.id.plus_btn)
        minusBtn = find(R.id.minus_btn)

        title = intent.getStringExtra(TITLE)
        rating.text = "Rating: "+ intent.getDoubleExtra(RATING, -1.0)
        val amount = intent.getDoubleExtra(PRICE, 0.0)

        price.text = "Price: ${amount}"
        val qty = intent.getIntExtra(QUANTITY, -1)
        val url = intent.getStringExtra(IMAGE_URL)
        icon.load(url)

        plusBtn.setOnClickListener {
            toast("Added to cart!") }

        minusBtn.setOnClickListener {
            toast("removed from cart!") }



    }
}
