package com.example.ebuy

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class PaymentMethod : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method2)

        supportActionBar?.hide()

        var btn_payment_method_back = findViewById<ImageButton>(R.id.btn_back_in_methods)


        var product_id = intent
        var pro_id = product_id.getStringExtra("pro_id")

        var customerid = Glob.Companion.customer_id

        /*
        * 1-> VISA
        * 2-> MASTER
        * 3-> PAYPAL
        * 4-> CASH
        * */
        var payment_m = 0

        var r_btn_via = findViewById<RadioButton>(R.id.radioButton_visa)
        var r_btn_master = findViewById<RadioButton>(R.id.radioButton_master)
        var r_btn_paypal = findViewById<RadioButton>(R.id.radioButton_paypal)
        var r_btn_cash = findViewById<RadioButton>(R.id.radioButton_cash_on_delivery)

        var btn_processed = findViewById<Button>(R.id.btn_proceed)

        r_btn_via.setOnClickListener() {
            payment_m = 1
        }
        r_btn_master.setOnClickListener() {
            payment_m = 2
        }
        r_btn_paypal.setOnClickListener() {
            payment_m = 3
        }
        r_btn_cash.setOnClickListener() {
            payment_m = 4
        }

        btn_processed.setOnClickListener() {

            SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Do you want to buy this?")
                .setCancelButton("Cancel",SweetAlertDialog.OnSweetClickListener{
                    it.dismissWithAnimation()
                })
                .setConfirmButton("Yes",SweetAlertDialog.OnSweetClickListener {

                    if (payment_m != 0) {

                        /*if (payment_m == 1) {

                        } else if (payment_m == 2) {

                        } else if (payment_m == 3) {

                        } else if (payment_m == 4) {

                        }*/

                        val request = JsonArrayRequest(
                            Request.Method.GET,
                            "https://duropackaging.com/sv3/customerorder?ProductID=" + pro_id + "&CustomerID=" + customerid + "&Quantity=1",
                            JSONArray(), { response ->
                                try {
                                    var status = response
                                    var purchase = status.getJSONObject(0).getString("Status")
                                    var orderid = status.getJSONObject(0).getString("OrderID")
                                    if (purchase.toInt() == 1) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Order Placed Successfully",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        Toast.makeText(
                                            applicationContext,
                                            "Your order id is : " + orderid,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            applicationContext,
                                            "Order Placed Not Successful",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

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
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Please select a payment method",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    it.dismissWithAnimation()
                })
                .show()






        }

        btn_payment_method_back.setOnClickListener() {
            onBackPressed()
        }
    }
}