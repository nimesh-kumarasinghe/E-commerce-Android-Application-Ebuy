package com.example.ebuy

data class Order(
    var order_id: String,
    var order_amount: String,
    var order_date: String,
    var status: String,
    var product_name: String,
    var order_qty: String,
    var product_img: String
)
