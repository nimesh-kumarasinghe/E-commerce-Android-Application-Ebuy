package com.example.ebuy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class splash_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        Handler().postDelayed({

            //get customer data
            val customer_sv = getSharedPreferences("customer_data_sv", MODE_PRIVATE)
            if (customer_sv.getString("logged", "").toString() == "1") {
                Glob.Companion.customer_id = customer_sv.getString("id", "").toString()

                //GET USER DETAILS
                val request = JsonArrayRequest(
                    Request.Method.GET,
                    "https://duropackaging.com/sv4/customers?id=" + Glob.customer_id + "",
                    JSONArray(), { response ->
                        try {
                            var user_data = response
                            Glob.user_first_name = user_data.getJSONObject(0).getString("FirstName")
                            Glob.user_last_name = user_data.getJSONObject(0).getString("LastName")
                            Glob.user_address = user_data.getJSONObject(0).getString("Address")
                            Glob.user_postal_code =
                                user_data.getJSONObject(0).getString("PostalCode")
                            Glob.user_country = user_data.getJSONObject(0).getString("Country")
                            Glob.user_city = user_data.getJSONObject(0).getString("City")
                            Glob.user_mobile_number =
                                user_data.getJSONObject(0).getString("PhoneNo")
                            Glob.user_email = user_data.getJSONObject(0).getString("Email")


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

                var home = Intent(this@splash_screen, Home::class.java)
                startActivity(home)
                finish()

                //GET USER DETAILS
            }
            //get customer data
            else {
                var intent = Intent(this@splash_screen, Login::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)

    }
}