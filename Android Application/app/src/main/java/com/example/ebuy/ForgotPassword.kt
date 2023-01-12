package com.example.ebuy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        supportActionBar?.hide()

        var btn_otp = findViewById<Button>(R.id.btn_otp)

        var customer_email = findViewById<EditText>(R.id.txt_reset_pw_email)

        btn_otp.setOnClickListener()
        {
            var validatex: Boolean = true
            if (Validatetxt(customer_email) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Email Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (emailValidate(customer_email) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Invalid email address",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (validatex == true) {
                Toast.makeText(applicationContext, "Please wait...", Toast.LENGTH_SHORT).show()
                Toast.makeText(applicationContext, "OTP is sent to your Email", Toast.LENGTH_SHORT)
                    .show()
                val request = JsonArrayRequest(
                    Request.Method.GET,
                    "https://duropackaging.com/sv4/resetpassword?Email=" + customer_email.text.toString() + " ",
                    JSONArray(), { response ->
                        try {
                            var status = response
                            var customer_found = status.getJSONObject(0).getString("Status")

                            if (customer_found.toInt() == 1) {
                                Log.e("customer_found", "TRUE")
                                var onetimecode = status.getJSONObject(0).getString("Code")
                                var customerid = status.getJSONObject(0).getString("CustomerID")

                                var otp_intent = Intent(this@ForgotPassword, TypeOTP::class.java)
                                otp_intent.putExtra("code", onetimecode.toString())
                                otp_intent.putExtra("cusid", customerid.toString())
                                startActivity(otp_intent)
                                finish()

                            } else {
                                Log.e("customer_found", "FALSE")
                                Toast.makeText(
                                    applicationContext, "Invalid User Please Try Again",
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

    fun emailValidate(emailtxt: EditText): Boolean {
        var res: Boolean = true

        var emailx = emailtxt.text

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailx).matches() == false) {
            res = false
        }

        return res
    }
}