package com.example.ebuy

import android.R.attr.password
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import java.io.*


class SignIn : AppCompatActivity() {

    lateinit var txt_email: EditText
    lateinit var txt_password: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        supportActionBar?.hide()


        txt_email = findViewById(R.id.txt_email)
        txt_password = findViewById(R.id.txt_password)

        var btn_acc_login = findViewById<Button>(R.id.btn_login_user)
        var txt_forgot_pw = findViewById<TextView>(R.id.txt_forgot_pw)



        btn_acc_login.setOnClickListener()
        {
            var validatex: Boolean = true

            if (Validatetxt(txt_email) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Email Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (emailValidate(txt_email) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Invalid email address",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (Validatetxt(txt_password) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Password Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            if (validatex == true) {

                val request = JsonArrayRequest(
                    Request.Method.GET,
                    "https://duropackaging.com/sv4/validate?Email=" + txt_email.text.toString() + "&CPassword=" + txt_password.text.toString() + "",
                    JSONArray(), { response ->
                        try {
                            var status = response
                            var logged = status.getJSONObject(0).getString("Status")

                            if (logged.toInt() == 1) {
                                Log.e("LOGGED", "TRUE")
                                var customer_id = status.getJSONObject(0).getString("CustomerID")


                                Glob.Companion.customer_id = customer_id.toString()
                                Log.e("xxx", Glob.Companion.customer_id)
                                Toast.makeText(
                                    applicationContext,
                                    "Sign In Successfully",
                                    Toast.LENGTH_LONG
                                ).show()

                                //save data on cache
                                val customer_sv =
                                    getSharedPreferences("customer_data_sv", MODE_PRIVATE)
                                var editor = customer_sv.edit()
                                editor.putString("logged", "1")
                                editor.putString("id", customer_id)
                                editor.commit()
                                //save data on cache

                                //GET USER DETAILS

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

                                request.setRetryPolicy(
                                    DefaultRetryPolicy(
                                        10000,
                                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                                    )
                                )

                                Volley.newRequestQueue(applicationContext).add(request)

                                //GET USER DETAILS

                                var home = Intent(this@SignIn, Home::class.java)
                                startActivity(home)
                                finish()

                            } else {
                                Log.e("LOGGED", "FALSE")
                                Toast.makeText(
                                    applicationContext,
                                    "Invalid User Email And Password",
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
                Volley.newRequestQueue(applicationContext).add(request)
            }
        }

        txt_forgot_pw.setOnClickListener()
        {
            var forgot_pw_intent = Intent(this@SignIn, ForgotPassword::class.java)
            startActivity(forgot_pw_intent)
            finish()
        }
    }

    fun Validatetxt(textx: EditText): Boolean {

        var res: Boolean = true
        if (textx.text.length == 0) {
            res = false
        }
        return res
    }

    fun emailValidate(emailtxt: EditText): Boolean {
        var res: Boolean = true

        var emailx = emailtxt.text

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailx).matches() == false) {
            res = false
        }

        return res
    }
}