package com.example.ebuy

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONException

class ProductActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        supportActionBar?.hide()


        var btn_back = findViewById<ImageButton>(R.id.btn_back)

        var product_id = intent
        var pro_id = product_id.getStringExtra("proid")

        var product_name = findViewById<TextView>(R.id.txt_product_name)
        var product_price = findViewById<TextView>(R.id.txt_price)
        var product_cate = findViewById<TextView>(R.id.txt_product_category_name)
        var product_qty = findViewById<TextView>(R.id.txt_quantity)
        var product_desc = findViewById<TextView>(R.id.txt_description)

        var product_img = findViewById<ImageView>(R.id.img_product)

        var btn_buy = findViewById<Button>(R.id.btn_buynow)
        var btn_add_to_cart = findViewById<Button>(R.id.btn_add_to_cart)

        var customerid = Glob.Companion.customer_id

        btn_back.setOnClickListener()
        {
            onBackPressed()
        }

        val request = JsonArrayRequest(
            Request.Method.GET,
            "https://duropackaging.com/sv2/products?id=" + pro_id + "",
            JSONArray(), { response ->
                try {
                    var product_data = response
                    product_name.text =
                        product_data.getJSONObject(0).getString("ProductName").toString()
                    product_price.text =
                        "$" + product_data.getJSONObject(0).getString("Price").toString()
                    //product_cate.text = "Category : " + product_data.getJSONObject(0).getString("category_id").toString()
                    product_qty.text =
                        "Quantity : " + product_data.getJSONObject(0).getString("Quantity")
                            .toString()
                    product_desc.text =
                        product_data.getJSONObject(0).getString("PDescription").toString()

                    var img = "https://duropackaging.com/sv2/imgs/" + product_data.getJSONObject(0)
                        .getString("imgLocation").toString()
                    Glide.with(product_img)
                        .load(img)
                        .into(product_img);

                    //get cate name
                    var cate_id = product_data.getJSONObject(0).getString("category_id").toString()

                    val request = JsonArrayRequest(
                        Request.Method.GET,
                        "https://duropackaging.com/sv2/category?id=" + cate_id + " ",
                        JSONArray(), { response ->
                            try {
                                var catedata = response
                                product_cate.text = "Category : " + catedata.getJSONObject(0)
                                    .getString("CategoryName").toString()

                            } catch (e: JSONException) {

                            }
                            Log.e("response", response.toString())
                        }, { error ->
                            Log.e("response", error.toString())
                        }
                    )

                    Volley.newRequestQueue(applicationContext).add(request)

                    //

                } catch (e: JSONException) {

                }
                Log.e("response", response.toString())
            }, { error ->
                Log.e("response", error.toString())
            }
        )

        request.setRetryPolicy(
            DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )

        Volley.newRequestQueue(applicationContext).add(request)

        btn_buy.setOnClickListener() {

            var paymentMethod = Intent(this, PaymentMethod::class.java)
            paymentMethod.putExtra("pro_id", pro_id)
            startActivity(paymentMethod)

            /////////////Buy now fun////////////////
            /*val request = JsonArrayRequest(
                Request.Method.GET,
                "https://duropackaging.com/sv3/customerorder?ProductID="+pro_id+"&CustomerID="+customerid+"&Quantity=1",
                JSONArray(), { response ->
                    try {
                        var status = response
                        var purchase = status.getJSONObject(0).getString("Status")
                        var orderid = status.getJSONObject(0).getString("OrderID")
                        if(purchase.toInt() == 1){
                            Toast.makeText(applicationContext,"Order Placed Successfully",Toast.LENGTH_LONG).show()
                            Toast.makeText(applicationContext,"Your order id is : " + orderid,Toast.LENGTH_LONG).show()
                        }
                        else{
                            Toast.makeText(applicationContext,"Order Placed Not Successful",Toast.LENGTH_LONG).show()
                        }

                    } catch (e: JSONException) {

                    }
                    Log.e("response", response.toString())
                }, { error ->
                    Log.e("response", error.toString())
                }
            )

            Volley.newRequestQueue(applicationContext).add(request)*/
            /////////////Buy now fun end////////////////
        }

        btn_add_to_cart.setOnClickListener() {

            var vali = 0

            var counter = 0
            while (counter < Glob.Companion.cart_pro_ids.count()) {
                if (Glob.Companion.cart_pro_ids[counter].toString() == pro_id.toString()) {
                    vali = 1
                }
                counter++
            }
            if (vali == 0) {
                Glob.Companion.cart_pro_ids.set(Glob.Companion.cart_pro_max_array_id, pro_id)
                Glob.Companion.cart_pro_max_array_id + 1
                Glob.Companion.db_con.execSQL("insert into cart values(" + pro_id + ")")
                Toast.makeText(applicationContext, "Product added to the cart", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "This product is already in the cart",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }

    }
}