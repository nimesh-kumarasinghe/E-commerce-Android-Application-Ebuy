package com.example.ebuy

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CatgeoryProductItems : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<CategryProductItemData>
    private lateinit var adapter: categryProductItemAdapter


    lateinit var productImage: Array<Int>
    lateinit var productName: Array<String>
    lateinit var productPrice: Array<String>
    lateinit var productQty: Array<String>
    lateinit var productDesc: Array<String>


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catgeory_product_items)

        supportActionBar?.hide()


        //dataInitialize()


        var btn_back = findViewById<ImageButton>(R.id.btn_back_button)

        btn_back.setOnClickListener()
        {
            onBackPressed()
        }
    }


}