package com.example.admin.orderfoodmvp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import com.example.admin.orderfoodmvp.R
import com.example.admin.orderfoodmvp.data.source.FoodRepository
import com.example.admin.orderfoodmvp.data.source.local.FoodLocalDataSource
import com.example.admin.orderfoodmvp.data.source.remote.FoodRemoteDataSource
import com.example.admin.orderfoodmvp.presenter.FoodContract
import com.example.admin.orderfoodmvp.presenter.description.DescriptionContract
import com.example.admin.orderfoodmvp.presenter.description.DescriptionPresenter
import com.example.admin.orderfoodmvp.util.load
import kotlinx.android.synthetic.main.activity_description.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class DescriptionActivity : AppCompatActivity(), DescriptionContract.View {
    override fun updateQuantity(message: String) {
        toast(message)
    }

    private lateinit var plusBtn: AppCompatButton
    private lateinit var minusBtn: AppCompatButton
    private lateinit var presenter: DescriptionContract.Presenter

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

        presenter = DescriptionPresenter(FoodRepository(FoodRemoteDataSource,
                            FoodLocalDataSource(application = application)), this)
        val itemName = intent.getStringExtra(TITLE)
        title = itemName
        rating.text = "Rating: "+ intent.getDoubleExtra(RATING, -1.0)
        val amount = intent.getDoubleExtra(PRICE, 0.0)

        price.text = "Price: ${amount}"
        val qty = intent.getIntExtra(QUANTITY, -1)
        val url = intent.getStringExtra(IMAGE_URL)
        icon.load(url)

        plusBtn.setOnClickListener { presenter.onAddBtnClicked(itemName)}

        minusBtn.setOnClickListener {presenter.onRemoveBtnClicked(itemName)}



    }
}
