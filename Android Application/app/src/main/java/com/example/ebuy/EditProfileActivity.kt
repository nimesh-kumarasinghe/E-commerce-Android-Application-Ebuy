package com.example.ebuy

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

import com.example.ebuy.Glob.Companion.customer_id
import com.example.ebuy.Glob.Companion.user_first_name
import com.example.ebuy.Glob.Companion.user_last_name
import com.example.ebuy.Glob.Companion.user_address
import com.example.ebuy.Glob.Companion.user_postal_code
import com.example.ebuy.Glob.Companion.user_country
import com.example.ebuy.Glob.Companion.user_city
import com.example.ebuy.Glob.Companion.user_mobile_number
import com.example.ebuy.Glob.Companion.user_email
import org.json.JSONArray
import org.json.JSONException

class EditProfileActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.hide()
        var btn_back = findViewById<ImageButton>(R.id.btn_back_button_user_profile)

        var btn_save = findViewById<Button>(R.id.btn_save)

        var u_first_name = findViewById<EditText>(R.id.txt_user_fname)
        var u_last_name = findViewById<EditText>(R.id.txt_user_lname)
        var u_adress = findViewById<EditText>(R.id.txt_user_address)
        var u_postal_code = findViewById<EditText>(R.id.txt_user_postal_code)
        var u_county = findViewById<EditText>(R.id.txt_user_country_name)
        var u_city = findViewById<EditText>(R.id.txt_user_city)
        var u_mobile_number = findViewById<EditText>(R.id.txt_user_telephone)

        u_first_name.setText(Glob.user_first_name)
        u_last_name.setText(Glob.user_last_name)
        u_adress.setText(Glob.user_address)
        u_postal_code.setText(Glob.user_postal_code)
        u_county.setText(Glob.user_country)
        u_city.setText(Glob.user_city)
        u_mobile_number.setText(Glob.user_mobile_number)

        btn_back.setOnClickListener()
        {
            onBackPressed()
        }

        btn_save.setOnClickListener()
        {
            var validatex: Boolean = true

            if (Validatetxt(u_first_name) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "First Name Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (Validatetxt(u_last_name) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Last Name Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (Validatetxt(u_adress) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Address Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (Validatetxt(u_postal_code) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Postal code Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (Validatetxt(u_county) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Country Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (Validatetxt(u_city) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "City Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (Validatetxt(u_mobile_number) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Mobile number Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (mobileValidate(u_mobile_number) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Please recheck your mobile number",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            if (validatex == true) {
                var url =
                    "https://duropackaging.com/sv4/update-customers?CustomerID=" + customer_id + "&FirstName=" + u_first_name.text + "&LastName=" + u_last_name.text + "&Address=" + u_adress.text + "&City=" + u_city.text + "&Country=" + u_county.text + "&PostalCode=" + u_postal_code.text + "&PhoneNo=" + u_mobile_number.text + ""

                Log.e("Gen Url", url)

                val request = JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    JSONArray(), { response ->
                        try {
                            var status = response
                            var logged = status.getJSONObject(0).getString("Status")

                            if (logged.toInt() == 1) {
                                Log.e("UPDATED", "TRUE")

                                Toast.makeText(
                                    applicationContext,
                                    "Account Details Updated",
                                    Toast.LENGTH_LONG
                                ).show()


                                val request = JsonArrayRequest(
                                    Request.Method.GET,
                                    "https://duropackaging.com/sv4/customers?id=" + Glob.Companion.customer_id + "",
                                    JSONArray(), { response ->
                                        try {
                                            var user_data = response
                                            Glob.Companion.user_first_name =
                                                user_data.getJSONObject(0).getString("FirstName")
                                            Glob.Companion.user_last_name =
                                                user_data.getJSONObject(0).getString("LastName")
                                            Glob.Companion.user_address =
                                                user_data.getJSONObject(0).getString("Address")
                                            Glob.Companion.user_postal_code =
                                                user_data.getJSONObject(0).getString("PostalCode")
                                            Glob.Companion.user_country =
                                                user_data.getJSONObject(0).getString("Country")
                                            Glob.Companion.user_city =
                                                user_data.getJSONObject(0).getString("City")
                                            Glob.Companion.user_mobile_number =
                                                user_data.getJSONObject(0).getString("PhoneNo")
                                            Glob.Companion.user_email =
                                                user_data.getJSONObject(0).getString("Email")

                                        } catch (e: JSONException) {

                                        }
                                        Log.e("response", response.toString())
                                    }, { error ->
                                        Log.e("response", error.toString())
                                    }
                                )

                                Volley.newRequestQueue(applicationContext).add(request)

                                //GET USER DETAILS

                            } else {
                                Log.e("UPDATED", "FALSE")
                                Toast.makeText(
                                    applicationContext, "UPDATE FAIL - PLEASE TRY AGAIN",
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
            }
        }
    }

    fun Validatetxt(textx: EditText): Boolean {

        var res: Boolean = true
        if (textx.text.length == 0) {
            res = false
        }
        return res
    }

    fun mobileValidate(numtxt: EditText): Boolean {
        var res: Boolean = true

        var xx = numtxt.text

        val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
        if (xx.matches(regex) != true) {
            res = false
        }
        if (xx.length != 10) {
            res = false
        }

        return res
    }
}