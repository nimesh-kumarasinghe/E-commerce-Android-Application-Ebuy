package com.example.ebuy

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class Login : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        if (Glob.cus_logout == true) {
            Log.e("User Logout", "User logged out !!")
            val customer_sv = getSharedPreferences("customer_data_sv", MODE_PRIVATE)
            var editor = customer_sv.edit()
            editor.putString("logged", "0")
            editor.putString("id", "null")
            editor.commit()
            Glob.Companion.cus_logout = false
        }

        var btn_sign_up = findViewById<Button>(R.id.btn_signup)
        var btn_sign_in = findViewById<Button>(R.id.btn_signin)

        btn_sign_up.setOnClickListener()
        {
            var sign_up = Intent(this@Login, SignUp::class.java)
            startActivity(sign_up)
        }
        btn_sign_in.setOnClickListener()
        {
            var sign_in = Intent(this@Login, SignIn::class.java)
            startActivity(sign_in)
        }

    }
}