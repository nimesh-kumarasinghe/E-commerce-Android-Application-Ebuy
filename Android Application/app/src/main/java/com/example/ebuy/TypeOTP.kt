package com.example.ebuy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.nio.BufferUnderflowException

class TypeOTP : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_type_otp)
        supportActionBar?.hide()

        var btn_otp_verify = findViewById<Button>(R.id.btn_otp_verify)
        var forgotpassword_code = intent
        var onetimecode = forgotpassword_code.getStringExtra("code")
        var customerid = forgotpassword_code.getStringExtra("cusid")
        var usercode = findViewById<EditText>(R.id.txt_otp)

        btn_otp_verify.setOnClickListener()
        {
            if (usercode.text.toString() == onetimecode) {
                Toast.makeText(
                    applicationContext, "Success",
                    Toast.LENGTH_LONG
                ).show()
                var reset_pw_intent = Intent(this@TypeOTP, ResetPassword::class.java)
                reset_pw_intent.putExtra("cusid", customerid.toString())
                startActivity(reset_pw_intent)
                finish()
            } else {
                Toast.makeText(
                    applicationContext, "Invalid Code",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}