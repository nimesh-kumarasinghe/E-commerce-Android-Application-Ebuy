package com.example.ebuy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class ResetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        supportActionBar?.hide()

        var btn_reset_pw = findViewById<Button>(R.id.btn_reset_pw)
        var reset_pwd = intent
        var customerid = reset_pwd.getStringExtra("cusid")

        var pw_one = findViewById<EditText>(R.id.txt_pw_one)
        var pw_two = findViewById<EditText>(R.id.txt_pw_two)

        btn_reset_pw.setOnClickListener()
        {
            var validatex: Boolean = true
            if (Validatetxt(pw_one) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Password Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (passwordValidate(pw_one) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Your password should be more than 8 characters",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (validatex == true) {
                if (pw_one.text.toString() == pw_two.text.toString()) {

                    val request = JsonArrayRequest(
                        Request.Method.GET,
                        "https://duropackaging.com/sv4/setnewpassword?CustomerID=" + customerid + "&NewPassword=" + pw_one.text.toString() + "",
                        JSONArray(), { response ->
                            try {
                                var status = response
                                var reset_password_status =
                                    status.getJSONObject(0).getString("Status")

                                if (reset_password_status.toInt() == 1) {
                                    Log.e("Password Rest Success", "TRUE")

                                    Toast.makeText(
                                        applicationContext, "Password is successfully reset",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    var login = Intent(this@ResetPassword, SignIn::class.java)
                                    startActivity(login)
                                    finish()

                                } else {
                                    Log.e("Password Rest Success", "FALSE")
                                    Toast.makeText(
                                        applicationContext, "Please Try Again",
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
                        applicationContext, "New Password And Confirm Password Not Match",
                        Toast.LENGTH_LONG
                    ).show()
                }

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

    fun passwordValidate(pwdtxt: EditText): Boolean {
        var res: Boolean = true
        var pwdx = pwdtxt.text

        if (pwdx.length < 8) {
            res = false
        }

        return res
    }
}